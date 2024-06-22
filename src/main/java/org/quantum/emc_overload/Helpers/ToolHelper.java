package org.quantum.emc_overload.Helpers;

import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.quantum.emc_overload.Matter.Block.TieredMatterBlock;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

public class ToolHelper {
    public static float calcDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state, MatterTier tier,float defaultDestroySpeed,int currentCharge) {
        boolean canMine = false;
        if (state.getBlock() instanceof TieredMatterBlock matterBlock) {
            if (matterBlock.getMatterTier().getProgression() <= tier.getProgression()) {
                canMine = true;
            }
        }
        if (state.getBlock() instanceof IMatterBlock matterBlock) {
            //get the projecte tier and compare it with our extension tiers
            if (matterBlock.getMatterType().getMatterTier() <= tier.getProgression()+2) {
                canMine = true;
            }
        }

        float destroySpeed = defaultDestroySpeed == 1.0F ? defaultDestroySpeed : defaultDestroySpeed + tier.getChargeModifier() * (float)currentCharge;

        return canMine ? 1200000.0F : destroySpeed + 48.0F;
    }
}
