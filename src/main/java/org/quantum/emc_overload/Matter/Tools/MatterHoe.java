package org.quantum.emc_overload.Matter.Tools;

import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.capability.ItemCapability;
import moze_intel.projecte.capability.ItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.utils.ToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.function.Consumer;

import static org.quantum.emc_overload.Helpers.ToolHelper.calcDestroySpeed;

public class MatterHoe extends HoeItem implements IItemCharge, IBarHelper {
    private final MatterTier matterTier;
    private final int numCharges;

    public MatterHoe(MatterTier matterTier, int numCharges, Item.Properties props) {
        super(matterTier, (int)(-matterTier.getAttackDamageBonus()), (float) matterTier.getProgression(), props);
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

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new ItemCapabilityWrapper(stack, new ItemCapability[]{new ChargeItemCapabilityWrapper()});
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return ToolHelper.tillAOE(context, context.getLevel().getBlockState(context.getClickedPos()), 0L);
    }
}