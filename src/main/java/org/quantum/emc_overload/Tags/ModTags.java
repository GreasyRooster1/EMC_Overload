package org.quantum.emc_overload.Tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.quantum.emc_overload.EMCOverload;


public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_STRANGE_MATTER_TOOL = tag("needs_strange_tool");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(EMCOverload.MODID,name));
        }
    }

    public static class Items{
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(EMCOverload.MODID,name));
        }
    }
}
