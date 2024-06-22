package org.quantum.emc_overload.Klein;

import org.quantum.emc_overload.Klein.Builder.KleinSeries;
import org.quantum.emc_overload.Matter.Builder.MatterType;
import org.quantum.emc_overload.Matter.Types.StrangeMatterType;

import java.util.ArrayList;

public class ModKleinSeries {
    public static ArrayList<KleinSeries> matterTypes = new ArrayList<>();
    public static void registerKleinSeries(){
        registerKleinSeries(new StrangeMatterType());
    }

    public static void registerKleinSeries(KleinSeries type){
        matterTypes.add(type);
    }
}
