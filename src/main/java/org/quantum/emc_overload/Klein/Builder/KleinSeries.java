package org.quantum.emc_overload.Klein.Builder;

import moze_intel.projecte.gameObjs.items.KleinStar;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

import static org.quantum.emc_overload.Items.ModItems.ITEMS;

public class KleinSeries {
    public long[] EMCValues;
    public String name;
    public ArrayList<RegistryObject<KleinExtensionItem>> items = new ArrayList<>();
    public KleinSeries(){

    }

    public void createItems(){
        for (KleinStar.EnumKleinTier kleinTier: KleinStar.EnumKleinTier.values()) {
            createKleinItem(kleinTier);
        }
    }

    public void createKleinItem(KleinStar.EnumKleinTier kleinTier){
        Item.Properties prop = new Item.Properties().stacksTo(1).rarity(getRarity(kleinTier));
        RegistryObject<KleinExtensionItem> item = ITEMS.register("klein_"+name+"_"+kleinTier.name,() -> new KleinExtensionItem(prop,kleinTier,this));
        items.add(item);
    }

    public Rarity getRarity(KleinStar.EnumKleinTier kleinTier){
        return Rarity.COMMON;
    }
}
