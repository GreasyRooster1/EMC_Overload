package org.quantum.emc_overload.Items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.quantum.emc_overload.EMCOverload;
import org.quantum.emc_overload.Matter.Builder.MatterItems;
import org.quantum.emc_overload.Matter.Builder.MatterType;

import static org.quantum.emc_overload.Matter.ModMatterTypes.matterTypes;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EMCOverload.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("emc_overload",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(matterTypes.get(0).items.item.get()))
                    .title(Component.translatable("creativetab.emc_overload_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        for(MatterType type: matterTypes){
                            MatterItems items = type.items;
                            pOutput.accept(items.item.get());
                            pOutput.accept(items.block.get());
                            pOutput.accept(items.sword.get());
                            pOutput.accept(items.pickaxe.get());
                            pOutput.accept(items.axe.get());
                            pOutput.accept(items.shovel.get());
                            pOutput.accept(items.hoe.get());
                            pOutput.accept(items.shears.get());
                            pOutput.accept(items.hammer.get());
                        }

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
