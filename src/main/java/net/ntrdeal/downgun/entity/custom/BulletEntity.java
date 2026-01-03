package net.ntrdeal.downgun.entity.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.ntrdeal.downgun.component.CardHolderComponent;
import net.ntrdeal.downgun.component.ModComponents;
import net.ntrdeal.downgun.entity.ModDamageSources;
import net.ntrdeal.downgun.entity.ModEntities;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class BulletEntity extends PersistentProjectileEntity {
    public static final TrackedData<Vector3f> STARTING_POSITION = DataTracker.registerData(BulletEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    public static final TrackedData<Vector3f> NORMALIZED_MOVEMENT = DataTracker.registerData(BulletEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    public static final TrackedData<Float> SPEED = DataTracker.registerData(BulletEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> GRAVITY = DataTracker.registerData(BulletEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> MAX_BOUNCES = DataTracker.registerData(BulletEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final boolean multishotBullet;
    @Nullable public final CardHolderComponent holder;

    public int bounces = 0;
    public int posAge = 0;
    public boolean leftOwner = false;

    public BulletEntity(EntityType<BulletEntity> type, World world) {
        super(type, world);
        this.multishotBullet = true;
        this.pickupType = PickupPermission.DISALLOWED;
        this.dataTracker.set(STARTING_POSITION, this.getPos().toVector3f());
        this.holder = null;
    }

    public BulletEntity(double x, double y, double z, World world) {
        super(ModEntities.BULLET_ENTITY, x, y, z, world, Items.IRON_NUGGET.getDefaultStack(), Items.IRON_NUGGET.getDefaultStack());
        this.multishotBullet = true;
        this.pickupType = PickupPermission.DISALLOWED;
        this.dataTracker.set(STARTING_POSITION, this.getPos().toVector3f());
        this.holder = null;
    }

    public BulletEntity(LivingEntity owner, World world, @Nullable ItemStack shotFrom, float speed, float gravity, int maxBounces, float divergence, boolean multishot) {
        super(ModEntities.BULLET_ENTITY, owner, world, Items.IRON_NUGGET.getDefaultStack(), shotFrom);
        this.multishotBullet = multishot;
        this.pickupType = PickupPermission.DISALLOWED;

        this.dataTracker.set(STARTING_POSITION, this.getPos().toVector3f());
        this.dataTracker.set(NORMALIZED_MOVEMENT, this.getStartingVelocity(owner, divergence).toVector3f());
        this.dataTracker.set(SPEED, speed * 0.05f);
        this.dataTracker.set(GRAVITY, gravity);
        this.dataTracker.set(MAX_BOUNCES, maxBounces);

        this.holder = owner instanceof PlayerEntity player ? ModComponents.CARD_HOLDER.get(player) : null;

        if (!this.multishotBullet) {
            MutableInt multishotCount = new MutableInt(0);
            if (this.holder != null) this.holder.getLayeredCards().forEach(entry -> entry.getKey().shootCountModifier(this.holder, multishotCount, entry.getValue()));
            for (int count = 0; count < multishotCount.getValue(); count++) {
                world.spawnEntity(new BulletEntity(owner, world, shotFrom, speed, gravity, maxBounces, divergence, true));
            }
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(STARTING_POSITION, new Vector3f());
        builder.add(NORMALIZED_MOVEMENT, new Vector3f());
        builder.add(SPEED, 25f);
        builder.add(GRAVITY, 0f);
        builder.add(MAX_BOUNCES, 0);
    }

    @Override
    public void tick() {
        if (this.age > 750) this.discard();
        this.baseTick();
        if (this.holder != null) this.holder.forEach((card, level) -> card.bulletTick(this.holder, this, level));
        if (!this.leftOwner) this.leftOwner = this.shouldLeaveOwner();
        Vec3d pos = this.startingPos().add(this.normalMovement().multiply(this.speed() * this.posAge))
                .add(0d, -this.gravity() * this.posAge * this.posAge * 0.5f, 0d);
        HitResult hitResult = this.getWorld().raycast(new RaycastContext(this.getPos(), pos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) pos = hitResult.getPos();
        if (this.getEntityCollision(this.getPos(), pos) instanceof EntityHitResult result) hitResult = result.getEntity().canHit() && result.getEntity().isAlive() ? result : null;
        if (hitResult != null) this.hitOrDeflect(hitResult);
        this.updatePosition(pos.getX(), pos.getY(), pos.getZ());
        this.posAge++;
    }

    public Vec3d getStartingVelocity(Entity shooter, float divergence) {
        float f = -MathHelper.sin(shooter.getYaw() * (float) (Math.PI / 180.0)) * MathHelper.cos(shooter.getPitch() * (float) (Math.PI / 180.0));
        float g = -MathHelper.sin(shooter.getPitch() * (float) (Math.PI / 180.0));
        float h = MathHelper.cos(shooter.getYaw() * (float) (Math.PI / 180.0)) * MathHelper.cos(shooter.getPitch() * (float) (Math.PI / 180.0));
        return this.calculateVelocity(f, g, h, 1f, divergence);
    }

    @Override
    public Vec3d getVelocity() {
        return this.normalMovement();
    }

    @Override
    public boolean canHit(Entity entity) {
        return entity.canBeHitByProjectile() && this.leftOwner;
    }

    private boolean shouldLeaveOwner() {
        Entity owner = this.getOwner();
        if (owner != null) {
            for (Entity entity2 : this.getWorld()
                    .getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), entity -> !entity.isSpectator() && entity.canHit())) {
                if (entity2.getRootVehicle() == owner.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Vec3d startingPos() {
        return new Vec3d(this.dataTracker.get(STARTING_POSITION));
    }

    public Vec3d normalMovement() {
        return new Vec3d(this.dataTracker.get(NORMALIZED_MOVEMENT));
    }

    public float speed() {
        return this.dataTracker.get(SPEED);
    }

    public float gravity() {
        return this.dataTracker.get(GRAVITY);
    }

    public int maxBounces() {
        return this.dataTracker.get(MAX_BOUNCES);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Vec3d.CODEC.encodeStart(NbtOps.INSTANCE, this.startingPos()).result().ifPresent(element -> nbt.put("startingPosition", element));
        Vec3d.CODEC.encodeStart(NbtOps.INSTANCE, this.normalMovement()).result().ifPresent(element -> nbt.put("normalizedSpeed", element));
        nbt.putFloat("bulletSpeed", this.speed());
        nbt.putFloat("gravity", this.gravity());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Vec3d.CODEC.parse(NbtOps.INSTANCE, nbt.get("startingPosition")).result().ifPresent(vec3d -> this.dataTracker.set(STARTING_POSITION, vec3d.toVector3f()));
        Vec3d.CODEC.parse(NbtOps.INSTANCE, nbt.get("normalizedSpeed")).result().ifPresent(vec3d -> this.dataTracker.set(NORMALIZED_MOVEMENT, vec3d.toVector3f()));
        this.dataTracker.set(SPEED, nbt.getFloat("bulletSpeed"));
        this.dataTracker.set(GRAVITY, nbt.getFloat("gravity"));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = ModDamageSources.of(this).bullet(this, this.getOwner());
        ServerWorld world = this.getWorld() instanceof ServerWorld serverWorld ? serverWorld : null;
        DamageData data = this.getOwner() instanceof LivingEntity attacker && entity instanceof LivingEntity target ? new DamageData(14f, 1.5f, this.startingPos(), entityHitResult.getPos(), this, damageSource, attacker, target) : null;
        float damage = data != null ? Math.max(data.getTotalDamage(), 0f) : 14f;
        Entity owner = this.getOwner();
        if (entity.damage(damageSource, damage)) {
            if (entity.getType().equals(EntityType.ENDERMAN)) return;
            if (world != null) EnchantmentHelper.onTargetDamaged(world, entity, damageSource, this.getWeaponStack());
            if (owner instanceof PlayerEntity player && data != null) {
                ModComponents.CARD_HOLDER.get(player).forEach((card, level) -> card.postHit(data, level));
            }
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.bounces < this.maxBounces()) {
            reflectVelocity(blockHitResult);
            this.bounces++;
        } else this.discard();
    }

    private void reflectVelocity(BlockHitResult blockHitResult) {
        Direction direction = blockHitResult.getSide();
        Vec3d velocity = this.getVelocity();
        if (direction.getAxis() == Direction.Axis.X) {
            velocity = new Vec3d(-velocity.x, velocity.y, velocity.z);
        }
        if (direction.getAxis() == Direction.Axis.Y) {
            velocity = new Vec3d(velocity.x, -velocity.y * 1.5d, velocity.z);
        }
        if (direction.getAxis() == Direction.Axis.Z) {
            velocity = new Vec3d(velocity.x, velocity.y, -velocity.z);
        }
        this.dataTracker.set(STARTING_POSITION, blockHitResult.getPos().add(Vec3d.of(direction.getVector()).multiply(0.1d)).toVector3f());
        this.dataTracker.set(NORMALIZED_MOVEMENT, velocity.toVector3f());
        this.posAge = 0;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return Items.IRON_NUGGET.getDefaultStack();
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }
}
