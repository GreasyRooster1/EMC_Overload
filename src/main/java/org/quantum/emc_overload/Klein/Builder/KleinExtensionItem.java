package org.quantum.emc_overload.Klein.Builder;

import moze_intel.projecte.api.capabilities.block_entity.IEmcStorage;
import moze_intel.projecte.api.capabilities.item.IItemEmcHolder;
import moze_intel.projecte.capability.EmcHolderItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.integration.IntegrationHelper;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class KleinExtensionItem  extends ItemPE implements IItemEmcHolder, IBarHelper {
    public final KleinStar.EnumKleinTier tier;

    public KleinStar(Item.Properties props, KleinStar.EnumKleinTier tier) {
        super(props);
        this.tier = tier;
        this.addItemCapability(EmcHolderItemCapabilityWrapper::new);
        this.addItemCapability("curios", IntegrationHelper.CURIO_CAP_SUPPLIER);
    }

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return stack.hasTag();
    }

    public float getWidthForBar(ItemStack stack) {
        long starEmc = getEmc(stack);
        return starEmc == 0L ? 1.0F : (float)(1.0 - (double)starEmc / (double) EMCHelper.getKleinStarMaxEmc(stack));
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return this.getScaledBarWidth(stack);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return this.getColorForBar(stack);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && !FMLEnvironment.production) {
            setEmc(stack, EMCHelper.getKleinStarMaxEmc(stack));
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.pass(stack);
        }
    }

    public long insertEmc(@NotNull ItemStack stack, long toInsert, IEmcStorage.EmcAction action) {
        if (toInsert < 0L) {
            return this.extractEmc(stack, -toInsert, action);
        } else {
            long toAdd = Math.min(this.getNeededEmc(stack), toInsert);
            if (action.execute()) {
                ItemPE.addEmcToStack(stack, toAdd);
            }

            return toAdd;
        }
    }

    public long extractEmc(@NotNull ItemStack stack, long toExtract, IEmcStorage.EmcAction action) {
        if (toExtract < 0L) {
            return this.insertEmc(stack, -toExtract, action);
        } else {
            long storedEmc = this.getStoredEmc(stack);
            long toRemove = Math.min(storedEmc, toExtract);
            if (action.execute()) {
                ItemPE.setEmc(stack, storedEmc - toRemove);
            }

            return toRemove;
        }
    }

    public @Range(
            from = 0L,
            to = Long.MAX_VALUE
    ) long getStoredEmc(@NotNull ItemStack stack) {
        return ItemPE.getEmc(stack);
    }

    public @Range(
            from = 1L,
            to = Long.MAX_VALUE
    ) long getMaximumEmc(@NotNull ItemStack stack) {
        return EMCHelper.getKleinStarMaxEmc(stack);
    }

