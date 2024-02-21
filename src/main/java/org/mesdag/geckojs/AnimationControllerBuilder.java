package org.mesdag.geckojs;

import dev.latvian.mods.rhino.util.HideFromJS;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.Hashtable;

@SuppressWarnings("unused")
public class AnimationControllerBuilder<T extends GeoAnimatable> {
    private transient String name = "base_controller";
    private transient int transitionTickTime = 0;
    private transient AnimationController.AnimationStateHandler<T> animationStateHandler;
    private transient AnimationController.SoundKeyframeHandler<T> soundKeyframeHandler;
    private transient AnimationController.ParticleKeyframeHandler<T> particleKeyframeHandler;
    private final transient Hashtable<String, RawAnimation> animations = new Hashtable<>();

    public AnimationControllerBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    public AnimationControllerBuilder<T> transitionTickTime(int tick) {
        this.transitionTickTime = tick;
        return this;
    }

    public AnimationControllerBuilder<T> animationState(AnimationController.AnimationStateHandler<T> handler) {
        this.animationStateHandler = handler;
        return this;
    }

    public AnimationControllerBuilder<T> soundKeyframe(AnimationController.SoundKeyframeHandler<T> handler) {
        this.soundKeyframeHandler = handler;
        return this;
    }

    public AnimationControllerBuilder<T> particleKeyframe(AnimationController.ParticleKeyframeHandler<T> handler) {
        this.particleKeyframeHandler = handler;
        return this;
    }

    public AnimationControllerBuilder<T> triggerableAnim(String animName, RawAnimation animation) {
        animations.put(animName, animation);
        return this;
    }

    @HideFromJS
    public AnimationController<T> build(T animatable) {
        AnimationController<T> controller = new AnimationController<>(animatable, name, transitionTickTime, animationStateHandler);
        if (soundKeyframeHandler != null) controller.setSoundKeyframeHandler(soundKeyframeHandler);
        if (particleKeyframeHandler != null) controller.setParticleKeyframeHandler(particleKeyframeHandler);
        animations.forEach(controller::triggerableAnim);
        return controller;
    }
}
