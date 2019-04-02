package be.yildizgames.sdk.feature.project.model.items;

import be.yildizgames.module.graphic.particle.ParticleEmitter;

/**
 * @author Grégory Van den Borre
 */
public class ParticleEmitterDef {

    private ParticleEmitter.EmitterType type;

    private float minSpeed;
    private float maxSpeed;
    private float lifeTime;
    private float rate;

    public ParticleEmitterDef(ParticleEmitter.EmitterType type, float minSpeed, float maxSpeed, float lifeTime, float rate) {
        this.type = type;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.lifeTime = lifeTime;
        this.rate = rate;
    }

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
