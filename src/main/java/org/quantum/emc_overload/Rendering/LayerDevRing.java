package org.quantum.emc_overload.Rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
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

import static java.lang.Math.*;
import static net.minecraft.util.Mth.HALF_PI;
import static net.minecraft.util.Mth.TWO_PI;
import static org.quantum.emc_overload.Rendering.ModRenderType.DEV_RING_RENDERER;

public class LayerDevRing extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final UUID Q_UUID = UUID.fromString("3dcfb3de-80a2-4596-9d82-5bcb5b405676");
    private static final UUID C_UUID = UUID.fromString("ee06c1b0-5520-4cc1-add5-0aeaf95c78a9");
    private static final ResourceLocation QUANTUM1_LOC = EMCOverload.rl("textures/models/quantum1.png");
    private static final ResourceLocation QUANTUM2_LOC = EMCOverload.rl("textures/models/quantum2.png");
    private static final ResourceLocation CRAFTER1_LOC = EMCOverload.rl("textures/models/crafter1.png");
    private static final int[] cColor = {0, 255, 162};
    private static final int[] qColor = {255, 0, 255};
    private static final int secondsPerRotation = 18;
    private final PlayerRenderer render;

    public LayerDevRing(PlayerRenderer renderer) {
        super(renderer);
        this.render = renderer;
    }

    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!player.isInvisible()) {

            boolean isQuantum = Q_UUID.equals(player.getUUID())||!FMLEnvironment.production;
            boolean isCrafter = C_UUID.equals(player.getUUID());

            if (isQuantum || isCrafter) {
                float timeVal = player.tickCount%(secondsPerRotation*20);
                float angle = (timeVal/(secondsPerRotation*20))*360;

                if(isQuantum){
                    createCircle(angle,0,3.3f,qColor,QUANTUM1_LOC,matrix,renderer,player.isCrouching());
                    createCircle(-angle,0,2.5f,qColor,QUANTUM2_LOC,matrix,renderer,player.isCrouching());
                }
                if(isCrafter){
                    createCircle(angle,.75f,1.1f,cColor,CRAFTER1_LOC,matrix,renderer,player.isCrouching());
                }
            }

        }
    }

    private void createCircle(float angle, double y, float scale, int[] color, ResourceLocation loc, PoseStack matrix, MultiBufferSource renderer, boolean isCrouching){
        matrix.pushPose();
        ModelPart jacket = this.render.getModel().jacket;
        jacket.translateAndRotate(matrix);
        double yShift = -0.497+y;

        if (isCrouching) {
            matrix.mulPose(Axis.XP.rotationDegrees(-28.64789F));
            yShift = -0.44+y;
        }

        matrix.mulPose(Axis.ZP.rotationDegrees(180F));
        matrix.mulPose(Axis.YP.rotationDegrees(angle));
        matrix.scale(scale, 3, scale);
        matrix.translate(-0.5, yShift, -0.5);


        VertexConsumer builder = renderer.getBuffer(DEV_RING_RENDERER.apply(loc));
        Matrix4f matrix4f = matrix.last().pose();
        builder.vertex(matrix4f, 0f,0f,0f).color(color[0], color[1], color[2], 255).uv(0.0F, 0.0F).endVertex();
        builder.vertex(matrix4f, 0f,0f,1f).color(color[0], color[1], color[2], 255).uv(0.0F, 1.0F).endVertex();
        builder.vertex(matrix4f, 1f,0f,1f).color(color[0], color[1], color[2], 255).uv(1.0F, 1.0F).endVertex();
        builder.vertex(matrix4f, 1f,0f,0f).color(color[0], color[1], color[2], 255).uv(1.0F, 0.0F).endVertex();
        matrix.popPose();
    }

}