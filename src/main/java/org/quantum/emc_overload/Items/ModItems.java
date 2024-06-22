package org.quantum.emc_overload.Items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.quantum.emc_overload.EMCOverload;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EMCOverload.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
