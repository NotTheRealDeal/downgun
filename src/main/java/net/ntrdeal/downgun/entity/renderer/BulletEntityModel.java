package net.ntrdeal.downgun.entity.renderer;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class BulletEntityModel extends EntityModel<BulletEntity> {
	public static final Identifier TEXTURE = DownGun.id("textures/entity/bullet.png");
	public static final EntityModelLayer BULLET = new EntityModelLayer(DownGun.id("bullet"),"main");

	private final ModelPart cube;

	public BulletEntityModel(ModelPart root) {
		this.cube = root.getChild("cube");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		modelData.getRoot().addChild("cube", ModelPartBuilder.create().uv(0, 0).cuboid(-3f, 0f, -3f, 6f, 6f, 6f), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 24, 24);
	}

	@Override
	public void setAngles(BulletEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.cube.render(matrices, vertices, light, overlay, color);
	}
}