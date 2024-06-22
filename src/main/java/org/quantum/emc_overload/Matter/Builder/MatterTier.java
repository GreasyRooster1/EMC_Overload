package org.quantum.emc_overload.Matter.Builder;

import moze_intel.projecte.PECore;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class MatterTier implements StringRepresentable, Tier{
    private final String name;
    private final float attackDamage;
    private final float efficiency;
    private final float chargeModifier;
    private final int harvestLevel;
    private final TagKey<Block> neededTag;
    private final MapColor mapColor;
    private final int progression;

    MatterTier(String name, int progression, float attackDamage, float efficiency, float chargeModifier, int harvestLevel, @Nullable TagKey neededTag, Tier previous, ResourceLocation next, MapColor mapColor) {
        this.name = name;
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.chargeModifier = chargeModifier;
        this.harvestLevel = harvestLevel;
        this.neededTag = neededTag;
        this.mapColor = mapColor;
        this.progression = progression;
        TierSortingRegistry.registerTier(this, PECore.rl(name), List.of(previous), next == null ? Collections.emptyList() : List.of(next));
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }

    public String toString() {
        return this.getSerializedName();
    }

    public int getUses() {
        return 0;
    }

    public float getChargeModifier() {
        return this.chargeModifier;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return 0;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public int getProgression() {
        return this.progression;
    }

    public @NotNull TagKey<Block> getTag() {
        return this.neededTag;
    }
}
