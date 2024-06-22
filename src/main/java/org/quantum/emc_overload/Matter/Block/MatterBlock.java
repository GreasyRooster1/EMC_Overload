package org.quantum.emc_overload.Matter.Block;

import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.blocks.IMatterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.quantum.emc_overload.Matter.Builder.MatterTier;

public class MatterBlock extends Block implements TieredMatterBlock {
    public final MatterTier matterTier;

    public MatterBlock(MatterTier tier) {
        super(BlockBehaviour.Properties.of().strength(3000000.0F+(tier.getProgression()*1000000.0F), 6000000.0F+(tier.getProgression()*1000000.0F)));
        this.matterTier = tier;
    }

    public MatterTier getMatterTier() {
        return this.matterTier;
    }
}
