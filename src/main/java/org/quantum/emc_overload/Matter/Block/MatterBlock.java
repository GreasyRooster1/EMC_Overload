package org.quantum.emc_overload.Matter.Block;

import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

import java.util.function.ToIntFunction;

public class MatterBlock extends Block implements TieredMatterBlock {
    public final MatterTier matterTier;

    public MatterBlock(MatterTier tier) {
        super(BlockBehaviour.Properties.of()
                .strength(3000000.0F+(tier.getProgression()*1000000.0F), 6000000.0F+(tier.getProgression()*1000000.0F))
                .lightLevel((block) -> 15)
        );
        this.matterTier = tier;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return (Boolean)p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    public MatterTier getMatterTier() {
        return this.matterTier;
    }
}
