package net.ntrdeal.downgun.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class ModEntities {

    public static final EntityType<BulletEntity> BULLET_ENTITY = Registry.register(Registries.ENTITY_TYPE, DownGun.id("bullet"),
            EntityType.Builder.<BulletEntity>create(BulletEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).eyeHeight(0.13f)
                    .maxTrackingRange(4).trackingTickInterval(5).build());

    public static void register() {}
}
