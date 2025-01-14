package org.quantum.emc_overload;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.quantum.emc_overload.Blocks.ModBlocks;
import org.quantum.emc_overload.Items.ModItems;
import org.quantum.emc_overload.Items.ModTabs;
import org.quantum.emc_overload.Rendering.LayerDevRing;
import org.slf4j.Logger;

import static org.quantum.emc_overload.Klein.ModKleinSeries.registerKleinSeries;
import static org.quantum.emc_overload.Matter.ModMatterTypes.registerMatters;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EMCOverload.MODID)
public class EMCOverload {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "emc_overload";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public EMCOverload() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        registerMatters();
        registerKleinSeries();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModTabs.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation("emc_overload", path);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
