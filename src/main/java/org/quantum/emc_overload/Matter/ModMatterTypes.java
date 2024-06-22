package org.quantum.emc_overload.Matter;

import org.quantum.emc_overload.Matter.Builder.MatterType;
import org.quantum.emc_overload.Matter.Types.StrangeMatterType;

import java.util.ArrayList;

public class ModMatterTypes {
    public static ArrayList<MatterType> matterTypes = new ArrayList<>();
    public static void registerMatters(){
        registerMatterType(new StrangeMatterType());
    }

    public static void registerMatterType(MatterType type){
        matterTypes.add(type);
    }
}
