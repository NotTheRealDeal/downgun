package net.ntrdeal.downgun;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.ntrdeal.downgun.entity.ModEntities;
import net.ntrdeal.downgun.entity.renderer.BulletEntityModel;
import net.ntrdeal.downgun.entity.renderer.BulletEntityRenderer;
import org.lwjgl.glfw.GLFW;

public class DownGunClient implements ClientModInitializer {
    public static final KeyBinding RELOAD = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.downgun.reload",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.downgun")
    );

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(BulletEntityModel.BULLET, BulletEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BULLET_ENTITY, BulletEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (RELOAD.wasPressed()) {
            }
        });
    }
}
