package net.onish.verity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.onish.verity.entity.VerityCompanionEntity;
import org.joml.Matrix4f;

public class VerityClientMod implements ClientModInitializer {

    public static final String MOD_ID = "verity";

    @Override
    public void onInitializeClient() {
        // Your entity renderer registration will go here
    }

    public static class CircleCompanionRenderer extends EntityRenderer<VerityCompanionEntity> {
        private static final ResourceLocation TEXTURE = 
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/default.png");

        public CircleCompanionRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(VerityCompanionEntity entity) {
            return TEXTURE;
        }

        @Override
        public void render(VerityCompanionEntity entity, float entityYaw, float partialTicks,
                           PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

            poseStack.pushPose();

            // Face the camera (Billboard)
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            PoseStack.Pose lastPose = poseStack.last();
            Matrix4f matrix = lastPose.pose();

            float radius = 0.5f;
            int segments = 32;

            vertexConsumer.addVertex(matrix, 0, 0, 0)
                    .setColor(255, 255, 255, 255)
                    .setUv(0.5f, 0.5f)
                    .setOverlay(0)
                    .setLight=packedLight
                    .setNormal(lastPose, 0, 0, 1);

            for (int i = 0; i <= segments; i++) {
                float angle = (float) (i * 2 * Math.PI / segments);
                float x = (float) (Math.cos(angle) * radius);
                float y = (float) (Math.sin(angle) * radius);

                float u = 0.5f + (float) (Math.cos(angle) * 0.5f);
                float v = 0.5f - (float) (Math.sin(angle) * 0.5f);

                vertexConsumer.addVertex(matrix, x, y, 0)
                        .setColor(255, 255, 255, 255)
                        .setUv(u, v)
                        .setOverlay(0)
                        .setLight=packedLight
                        .setNormal(lastPose, 0, 0, 1);
            }

            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }
}
