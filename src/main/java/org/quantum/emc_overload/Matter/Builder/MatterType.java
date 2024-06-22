package org.quantum.emc_overload.Matter.Builder;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import org.quantum.emc_overload.Matter.Block.MatterBlock;
import org.quantum.emc_overload.Matter.Tools.*;

import static moze_intel.projecte.gameObjs.EnumMatterType.RED_MATTER;
import static org.quantum.emc_overload.Blocks.ModBlocks.registerBlock;
import static org.quantum.emc_overload.Items.ModItems.ITEMS;

public class MatterType{
    public String name;
    public int progression;
    public MatterItems items = new MatterItems();
    public MatterTier tier;
    public TagKey<Block> toolTag;

    public MatterType(String _name, int _progression){
        name=_name;
        progression=_progression;
    }

    public void createTier(){
        tier = new MatterTier(name,
                progression,
                progression+5,
                progression*2+16,
                progression*2+14,
                progression+5,
                toolTag,
                RED_MATTER,
                (ResourceLocation)null,
                MapColor.COLOR_GREEN);
    }

    public void createItems(){
        items.item = getItem();
        items.block = getBlock();
        items.sword = getSword();
        items.pickaxe = getPickaxe();
        items.axe = getAxe();
        items.shovel = getShovel();
        items.hoe = getHoe();
        items.shears = getShears();
        items.hammer = getHammer();
    }

    public RegistryObject<Item> getItem(){
        return ITEMS.register(name+"_matter",() -> new Item(new Item.Properties()));
    }

    public RegistryObject<Block> getBlock(){
        return registerBlock(name+"_matter_block",
                () -> new MatterBlock(tier));
    }

    public RegistryObject<Item> getSword(){
        return ITEMS.register(name+"_matter_sword",
                () -> new MatterSword(tier,progression+4,progression*3+15, new Item.Properties().fireResistant()));
    }

    public RegistryObject<Item> getPickaxe(){
        return ITEMS.register(name+"_matter_pickaxe",
                () -> new MatterPickaxe(tier,progression+4,new Item.Properties().fireResistant()));
    }
    public RegistryObject<Item> getAxe(){
        return ITEMS.register(name+"_matter_axe",
                () -> new MatterAxe(tier,progression+4,new Item.Properties().fireResistant()));
    }
    public RegistryObject<Item> getShovel(){
        return ITEMS.register(name+"_matter_shovel",
                () -> new MatterShovel(tier,progression+4,new Item.Properties().fireResistant()));
    }
    public RegistryObject<Item> getHoe(){
        return ITEMS.register(name+"_matter_hoe",
                () -> new MatterHoe(tier,progression+4,new Item.Properties().fireResistant()));
    }
    public RegistryObject<Item> getShears(){
        return ITEMS.register(name+"_matter_shears",
                () -> new MatterShears(tier,progression+4,new Item.Properties().fireResistant()));
    }
    public RegistryObject<Item> getHammer(){
        return ITEMS.register(name+"_matter_hammer",
                () -> new MatterHammer(tier,progression+4,new Item.Properties().fireResistant()));
    }

}
