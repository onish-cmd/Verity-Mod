package com.onish.verity.client;

import com.onish.verity.entity.VerityCompanionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class VerityClientMod implements ClientModInitializer {
    public static final String MOD_ID = "verity";

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(
            net.minecraft.world.entity.EntityType.byString(MOD_ID + ":verity_companion").orElseThrow(),
            CircleCompanionRenderer::new
        );
    }

    public static class CircleCompanionRenderer extends EntityRenderer<VerityCompanionEntity, EntityRenderer.State> {
        private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/default.png");

        public CircleCompanionRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(VerityCompanionEntity entity) {
            return TEXTURE;
        }

        @Override
        public void render(VerityCompanionEntity entity, EntityRenderer.State state, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
            poseStack.pushPose();
            
            poseStack.translate(0.0D, 0.5D, 0.0D);
            
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            Matrix4f matrix = poseStack.last().pose();

            float size = 0.5F;            
            consumer.addVertex(matrix, -size, -size, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setLightmap(packedLight);
            consumer.addVertex(matrix, size, -size, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setLightmap(packedLight);
            consumer.addVertex(matrix, size, size, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setLightmap(packedLight);
            consumer.addVertex(matrix, -size, size, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setLightmap(packedLight);

            poseStack.popPose();
            super.render(entity, state, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }

        @Override
        public EntityRenderer.State createRenderState() {
            return new EntityRenderer.State();
        }
    }
}
