package org.quantum.emc_overload.Rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.quantum.emc_overload.EMCOverload;

import java.util.UUID;

import static org.quantum.emc_overload.Rendering.ModRenderType.DEV_RING_RENDERER;

public class LayerDevRing extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final UUID Q_UUID = UUID.fromString("3dcfb3de-80a2-4596-9d82-5bcb5b405676");
    private static final UUID C_UUID = UUID.fromString("ee06c1b0-5520-4cc1-add5-0aeaf95c78a9");
    private static final ResourceLocation Q1_LOC = EMCOverload.rl("textures/models/quantum1.png");
    private static final ResourceLocation Q2_LOC = EMCOverload.rl("textures/models/quantum2.png");
    private static final ResourceLocation C1_LOC = EMCOverload.rl("textures/models/crafter1.png");
    private final PlayerRenderer render;

    public LayerDevRing(PlayerRenderer renderer) {
        super(renderer);
        this.render = renderer;
    }

    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!player.isInvisible()) {
            boolean isQ = Q_UUID.equals(player.getUUID())||!FMLEnvironment.production;
            if (!FMLEnvironment.production || Q_UUID.equals(player.getUUID()) || C_UUID.equals(player.getUUID())) {
                matrix.pushPose();
                ((PlayerModel<?>)this.render.getModel()).jacket.translateAndRotate(matrix);
                double yShift = -0.497;

                if (player.isCrouching()) {
                    matrix.mulPose(Axis.XP.rotationDegrees(-28.64789F));
                    yShift = -0.44;
                }

                matrix.mulPose(Axis.ZP.rotationDegrees(180.0F));
                matrix.scale(3.0F, 3.0F, 3.0F);
                matrix.translate(-0.5, yShift, -0.5);

                ResourceLocation loc = Q1_LOC;

                if(isQ){
                    loc = Q1_LOC;
                }
                if(C_UUID.equals(player.getUUID())){
                    loc = C1_LOC;
                }

                VertexConsumer builder = renderer.getBuffer((RenderType) DEV_RING_RENDERER.apply(loc));
                Matrix4f matrix4f = matrix.last().pose();
                builder.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 0, 255, 255).uv(0.0F, 0.0F).endVertex();
                builder.vertex(matrix4f, 0.0F, 0.0F, 1.0F).color(255, 0, 255, 255).uv(0.0F, 1.0F).endVertex();
                builder.vertex(matrix4f, 1.0F, 0.0F, 1.0F).color(255, 0, 255, 255).uv(1.0F, 1.0F).endVertex();
                builder.vertex(matrix4f, 1.0F, 0.0F, 0.0F).color(255, 0, 255, 255).uv(1.0F, 0.0F).endVertex();
                matrix.popPose();
            }

        }
    }

}