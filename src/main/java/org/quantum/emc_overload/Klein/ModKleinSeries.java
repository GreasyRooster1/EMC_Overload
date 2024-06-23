package org.quantum.emc_overload.Klein;

import org.quantum.emc_overload.Klein.Builder.KleinSeries;
import org.quantum.emc_overload.Klein.Types.KleinNovaSeries;
import org.quantum.emc_overload.Matter.Builder.MatterType;
import org.quantum.emc_overload.Matter.Types.StrangeMatterType;

import java.util.ArrayList;

public class ModKleinSeries {
    public static ArrayList<KleinSeries> kleinSeries = new ArrayList<>();
    public static void registerKleinSeries(){
        registerKleinSeries(new KleinNovaSeries());
    }

    public static void registerKleinSeries(KleinSeries type){
        kleinSeries.add(type);
    }
}
