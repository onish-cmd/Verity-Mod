package net.onish.verity.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class VerityCompanionEntity extends PathfinderMob {

    public VerityCompanionEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setNoAi(true);
    }

    @Override
    protected void registerGoals() {
        // Left completely empty to guarantee no AI goals are registered
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
