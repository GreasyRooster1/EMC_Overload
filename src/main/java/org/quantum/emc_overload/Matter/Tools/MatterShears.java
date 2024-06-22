package org.quantum.emc_overload.Matter.Tools;

import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.capability.ItemCapability;
import moze_intel.projecte.capability.ItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.PETags;
import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.ToolHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.function.Consumer;

import static org.quantum.emc_overload.Helpers.ToolHelper.calcDestroySpeed;

public class MatterShears  extends ShearsItem implements IItemCharge, IBarHelper {
    private final MatterTier matterTier;
    private final int numCharges;

    public MatterShears(MatterTier matterTier, int numCharges, Item.Properties props) {
        super(props);
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
        float speed = super.getDestroySpeed(stack, state);
        if (speed == 1.0F && state.is(PETags.Blocks.MINEABLE_WITH_PE_SHEARS)) {
            speed = this.matterTier.getSpeed();
        }

        return calcDestroySpeed(stack,state,matterTier,speed,this.getCharge(stack));

    }

    public int getNumCharges(@NotNull ItemStack stack) {
        return this.numCharges;
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new ItemCapabilityWrapper(stack, new ItemCapability[]{new ChargeItemCapabilityWrapper()});
    }

    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, BlockState state) {
        return state.is(PETags.Blocks.MINEABLE_WITH_PE_SHEARS) && TierSortingRegistry.isCorrectTierForDrops(this.matterTier, state);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemHelper.actionResultFromType(ToolHelper.shearEntityAOE(player, hand, 0L), player.getItemInHand(hand));
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        return ToolHelper.shearBlock(stack, pos, player).consumesAction();
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Level level = context.getLevel();
            BlockState state = level.getBlockState(context.getClickedPos());
            if (state.is(BlockTags.LEAVES)) {
                ToolHelper.clearTagAOE(level, player, context.getHand(), context.getItemInHand(), 0L, BlockTags.LEAVES);
            }
        }

        return InteractionResult.PASS;
    }
}