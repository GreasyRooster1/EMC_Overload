package org.quantum.emc_overload.Matter.Types;

import org.quantum.emc_overload.Matter.Builder.MatterType;
import org.quantum.emc_overload.Tags.ModTags;

public class StrangeMatterType extends MatterType {
    public StrangeMatterType() {
        super("strange", 0);

        createTier();
        createItems();
    }
}
