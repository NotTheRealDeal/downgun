package net.ntrdeal.downgun.entity.renderer;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class BulletEntityRenderer extends EntityRenderer<BulletEntity> {
    private final BulletEntityModel model;

    public BulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new BulletEntityModel(ctx.getPart(BulletEntityModel.BULLET));
    }

    @Override
    public void render(BulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
        this.model.render(matrices, consumer, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0xffedf145);
//        this.model.render(matrices, consumer, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0xfff44e38);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return BulletEntityModel.TEXTURE;
    }
}