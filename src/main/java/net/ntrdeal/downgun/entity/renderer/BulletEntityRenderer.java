package net.ntrdeal.downgun.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.util.Identifier;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class BulletEntityRenderer extends EntityRenderer<BulletEntity> {
    public BulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return TridentEntityModel.TEXTURE;
    }
}
