package net.kalf.kalfswarmod.entity.custom;

import net.kalf.kalfswarmod.entity.ModEntities;
import net.kalf.kalfswarmod.item.ModItems;
import net.kalf.kalfswarmod.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class LargeBulletProjectileEntity extends ThrowableItemProjectile {
    public LargeBulletProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    private float bulletDamage;

    public void setDamage(float newDamage) {
        this.bulletDamage = newDamage;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.LARGE_BULLET_ENTITY.get();
    }

    public LargeBulletProjectileEntity(Level pLevel) {
        super(ModEntities.LARGE_BULLET_PROJECTILE.get(), pLevel);
        this.setNoGravity(true);
    }

    public LargeBulletProjectileEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.LARGE_BULLET_PROJECTILE.get(), livingEntity, pLevel);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity hitEntity = pResult.getEntity();

        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), this.bulletDamage);
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.BULLET_RICOCHET.get(), SoundSource.PLAYERS, 4.0f, 1.0f);
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY()+0.5d, this.getZ(), 0d, 0d, 0d);

        }
    }
}
