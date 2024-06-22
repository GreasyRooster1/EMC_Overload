package org.quantum.emc_overload.Matter.Tools;

import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.capability.ItemCapability;
import moze_intel.projecte.capability.ItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.utils.ToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.quantum.emc_overload.Helpers.ToolHelper.calcDestroySpeed;

public abstract class MatterTool extends DiggerItem implements IItemCharge, IBarHelper {
    private final List<Supplier<ItemCapability<?>>> supportedCapabilities = new ArrayList();
    protected final MatterTier matterTier;
    private final int numCharges;

    public MatterTool(MatterTier matterTier, TagKey<Block> blocks, float damage, float attackSpeed, int numCharges, Item.Properties props) {
        super(damage, attackSpeed, matterTier, blocks, props);
        this.matterTier = matterTier;
        this.numCharges = numCharges;
        this.addItemCapability(ChargeItemCapabilityWrapper::new);
    }

    protected void addItemCapability(Supplier<ItemCapability<?>> capabilitySupplier) {
        this.supportedCapabilities.add(capabilitySupplier);
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
        return (ICapabilityProvider)(this.supportedCapabilities.isEmpty() ? super.initCapabilities(stack, nbt) : new ItemCapabilityWrapper(stack, this.supportedCapabilities));
    }

    protected float getShortCutDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        return super.getDestroySpeed(stack, state);
    }
}