package org.quantum.emc_overload.Matter.Types;

import org.quantum.emc_overload.Matter.Builder.MatterType;
import org.quantum.emc_overload.Tags.ModTags;

public class FluxMatterType extends MatterType {
    public FluxMatterType() {
        super("flux", 1);

        createTier();
        createItems();
    }
}
