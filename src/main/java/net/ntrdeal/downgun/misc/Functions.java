package net.ntrdeal.downgun.misc;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;

public class Functions {
    public static  <T extends ParticleEffect> void spawnParticles(Entity entity, T particle, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        if (entity.getWorld() instanceof ServerWorld world) {
            double x = entity.getX(), y = entity.getEyeY(), z = entity.getZ();
            ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(particle, true, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
            world.getPlayers().forEach(player -> world.sendToPlayerIfNearby(player, true, x, y, z, particleS2CPacket));
        }
    }
}
