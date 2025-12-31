package net.ntrdeal.downgun.mixin;

import net.minecraft.entity.damage.DamageSources;
import net.minecraft.registry.DynamicRegistryManager;
import net.ntrdeal.downgun.entity.ModDamageSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public class DamageSourcesMixin implements ModDamageSources.ModDamages {
    @Unique private ModDamageSources sources;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ntrdeal$addSources(DynamicRegistryManager registryManager, CallbackInfo ci) {
        this.sources = new ModDamageSources((DamageSources)(Object)this);
    }

    @Override
    public ModDamageSources ntrdeal$getSources() {
        return this.sources;
    }
}
