package org.quantum.emc_overload.Matter.Tools;

import com.google.common.collect.Multimap;
import moze_intel.projecte.api.capabilities.item.IExtraFunction;
import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.capability.ExtraFunctionItemCapabilityWrapper;
import moze_intel.projecte.capability.ItemCapability;
import moze_intel.projecte.capability.ItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.PETags;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.utils.PlayerHelper;
import moze_intel.projecte.utils.ToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MatterSword extends SwordItem implements IExtraFunction, IItemCharge, IBarHelper {
    private final List<Supplier<ItemCapability<?>>> supportedCapabilities = new ArrayList();
    private final ToolHelper.ChargeAttributeCache attributeCache = new ToolHelper.ChargeAttributeCache();
    private final MatterTier matterTier;
    private final int numCharges;

    public MatterSword(MatterTier matterTier, int numCharges, int damage, Item.Properties props) {
        super(matterTier, damage, -2.4F, props);
        this.matterTier = matterTier;
        this.numCharges = numCharges;
        this.addItemCapability(ChargeItemCapabilityWrapper::new);
        this.addItemCapability(ExtraFunctionItemCapabilityWrapper::new);
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
        float speed = super.getDestroySpeed(stack, state);
        if (speed == 1.0F && state.is(PETags.Blocks.MINEABLE_WITH_PE_SWORD)) {
            speed = this.matterTier.getSpeed();
        }

        return speed == 1.0F ? speed : speed + matterTier.getChargeModifier() * (float)this.getCharge(stack);
    }

    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, BlockState state) {
        return state.is(PETags.Blocks.MINEABLE_WITH_PE_SWORD) && TierSortingRegistry.isCorrectTierForDrops(this.matterTier, state);
    }

    public int getNumCharges(@NotNull ItemStack stack) {
        return this.numCharges;
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return (ICapabilityProvider)(this.supportedCapabilities.isEmpty() ? super.initCapabilities(stack, nbt) : new ItemCapabilityWrapper(stack, this.supportedCapabilities));
    }

    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity damaged, @NotNull LivingEntity damager) {
        ToolHelper.attackWithCharge(stack, damaged, damager, 1.0F);
        return true;
    }

    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        int charge = this.getCharge(stack);
        return target.getBoundingBox().inflate((double)charge, (double)charge / 4.0, (double)charge);
    }

    public boolean doExtraFunction(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand) {
        if (player.getAttackStrengthScale(0.0F) == 1.0F) {
            ToolHelper.attackAOE(stack, player, this.slayAll(stack), this.getDamage(), 0L, hand);
            PlayerHelper.resetCooldown(player);
            return true;
        } else {
            return false;
        }
    }

    protected boolean slayAll(@NotNull ItemStack stack) {
        return false;
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot, ItemStack stack) {
        return this.attributeCache.addChargeAttributeModifier(super.getAttributeModifiers(slot, stack), slot, stack);
    }
}
