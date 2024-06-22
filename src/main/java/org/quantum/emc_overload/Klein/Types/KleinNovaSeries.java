package org.quantum.emc_overload.Klein.Types;

import org.quantum.emc_overload.Klein.Builder.KleinSeries;

public class KleinNovaSeries extends KleinSeries {
    public KleinNovaSeries(){
        super();

        EMCValues =new long[]{102_400_000L, 409_600_000L, 8_192_000_000L, 16_384_000_000L, 64_000_000_000L, 256_000_000_000L};
        name="nova";

        createItems();
    }
}
