package org.quantum.emc_overload.Matter.Tools;

import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.capability.ItemCapability;
import moze_intel.projecte.capability.ItemCapabilityWrapper;
import moze_intel.projecte.capability.ModeChangerItemCapabilityWrapper;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.gameObjs.items.IItemMode;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.ToolHelper;
import moze_intel.projecte.utils.text.ILangEntry;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.List;
import java.util.function.Consumer;

import static org.quantum.emc_overload.Helpers.ToolHelper.calcDestroySpeed;

public class MatterPickaxe extends PickaxeItem implements IItemCharge, IItemMode, IBarHelper {
    private final MatterTier matterTier;
    private final ILangEntry[] modeDesc;
    private final int numCharges;

    public MatterPickaxe(MatterTier matterTier, int numCharges, Item.Properties props) {
        super(matterTier, 4, -2.8F, props);
        this.modeDesc = new ILangEntry[]{PELang.MODE_PICK_1, PELang.MODE_PICK_2, PELang.MODE_PICK_3, PELang.MODE_PICK_4};
        this.matterTier = matterTier;
        this.numCharges = numCharges;
    }

    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return 0;
    }

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    public float getWidthForBar(ItemStack stack) {
        return 1.0F - this.getChargePercent(stack);
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return this.getScaledBarWidth(stack);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return this.getColorForBar(stack);
    }

    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        return calcDestroySpeed(stack,state,matterTier,super.getDestroySpeed(stack,state),this.getCharge(stack));
    }

    public int getNumCharges(@NotNull ItemStack stack) {
        return this.numCharges;
    }

    public ILangEntry[] getModeLangEntries() {
        return this.modeDesc;
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltips, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, level, tooltips, flags);
        tooltips.add(this.getToolTip(stack));
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new ItemCapabilityWrapper(stack, new ItemCapability[]{new ChargeItemCapabilityWrapper(), new ModeChangerItemCapabilityWrapper()});
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return ProjectEConfig.server.items.pickaxeAoeVeinMining.get() ? ItemHelper.actionResultFromType(ToolHelper.mineOreVeinsInAOE(player, hand), stack) : InteractionResultHolder.pass(stack);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && !ProjectEConfig.server.items.pickaxeAoeVeinMining.get()) {
            BlockPos pos = context.getClickedPos();
            return ItemHelper.isOre(context.getLevel().getBlockState(pos)) ? ToolHelper.tryVeinMine(player, context.getItemInHand(), pos, context.getClickedFace()) : InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity living) {
        ToolHelper.digBasedOnMode(stack, level, pos, living, (x$0, x$1, x$2) -> {
            return Item.getPlayerPOVHitResult(x$0, x$1, x$2);
        });
        return true;
    }
}