package org.quantum.emc_overload;

import moze_intel.projecte.rendering.LayerYue;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.quantum.emc_overload.Rendering.LayerDevRing;

import java.util.Iterator;

import static org.quantum.emc_overload.EMCOverload.MODID;


@Mod.EventBusSubscriber(
        modid = MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientRegistration {
    public ClientRegistration() {
    }
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {

        for (String skinName : event.getSkins()) {
            PlayerRenderer skin = (PlayerRenderer) event.getSkin(skinName);
            if (skin != null) {
                skin.addLayer(new LayerDevRing(skin));
            }
        }

    }
}
