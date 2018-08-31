package be.yildizgames.sdk.feature.project.model.items;

import be.yildizgames.module.graphic.particle.ParticleEmitter;

public class ParticleEmitterDef {

    private ParticleEmitter.EmitterType type;

    private float minSpeed;
    private float maxSpeed;
    private float lifeTime;
    private float rate;

    public ParticleEmitter.EmitterType getType() {
        return this.type;
    }

    public float getMinSpeed() {
        return minSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public float getRate() {
        return rate;
    }
}
