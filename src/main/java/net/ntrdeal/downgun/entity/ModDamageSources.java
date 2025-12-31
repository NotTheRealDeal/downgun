package net.ntrdeal.downgun.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.ntrdeal.downgun.DownGun;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {
    public static final RegistryKey<DamageType> BULLET = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, DownGun.id("bullet"));
    private final DamageSources sources;

    public ModDamageSources(DamageSources sources) {
        this.sources = sources;
    }

    public DamageSource bullet(PersistentProjectileEntity source, @Nullable Entity attacker) {
        return this.sources.create(BULLET, source, attacker);
    }

    public static ModDamageSources of(Entity entity) {
        return ((ModDamages)entity.getDamageSources()).ntrdeal$getSources();
    }

    public interface ModDamages {
        ModDamageSources ntrdeal$getSources();
    }
}
