package org.mesdag.geckojs.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;
import org.mesdag.geckojs.block.AnimatableBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AnimatableItemBuilder extends ItemBuilder {
    public final HashMap<ControllerInfo, Hashtable<String, RawAnimation>> animations = new HashMap<>();
    public final Hashtable<AnimationPredicateCallback, Tuple<String, String>> useTriggers = new Hashtable<>();
    public final Hashtable<AnimationPredicateCallback, Tuple<String, String>> finishUsingTriggers = new Hashtable<>();
    public final Hashtable<AnimationPredicateCallback, Tuple<String, String>> releaseUsingTriggers = new Hashtable<>();
    public final ExtendedGeoModel<AnimatableItem> itemModel = new ExtendedGeoModel<>();

    public AnimatableItemBuilder(ResourceLocation id) {
        super(id);
    }

    public AnimatableItemBuilder addUseAnimationTrigger(AnimationPredicateCallback callback, String controllerName, String animName) {
        useTriggers.put(callback, new Tuple<>(controllerName, animName));
        return this;
    }

    public AnimatableItemBuilder addFinishUsingAnimationTrigger(AnimationPredicateCallback callback, String controllerName, String animName) {
        useTriggers.put(callback, new Tuple<>(controllerName, animName));
        return this;
    }

    public AnimatableItemBuilder addReleaseUsingAnimationTrigger(AnimationPredicateCallback callback, String controllerName, String animName) {
        useTriggers.put(callback, new Tuple<>(controllerName, animName));
        return this;
    }

    public AnimatableItemBuilder addController(ControllerCallBack callBack) {
        animations.put(new ControllerInfo("base_controller", 0, callBack), null);
        return this;
    }

    public AnimatableItemBuilder addController(String name, ControllerCallBack callBack) {
        animations.put(new ControllerInfo(name, 0, callBack), null);
        return this;
    }

    public AnimatableItemBuilder addController(String name, ControllerCallBack callBack, Map<String, RawAnimation> triggers) {
        animations.put(new ControllerInfo(name, 0, callBack), (Hashtable<String, RawAnimation>) triggers);
        return this;
    }

    public AnimatableItemBuilder addController(String name, int transitionTickTime, ControllerCallBack callBack) {
        animations.put(new ControllerInfo(name, transitionTickTime, callBack), null);
        return this;
    }

    public AnimatableItemBuilder addController(String name, int transitionTickTime, ControllerCallBack callBack, Map<String, RawAnimation> triggers) {
        animations.put(new ControllerInfo(name, transitionTickTime, callBack), (Hashtable<String, RawAnimation>) triggers);
        return this;
    }

    public AnimatableItemBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableItem>> consumer){
        consumer.accept(itemModel.builder);
        return this;
    }

    @Override
    public AnimatableItem createObject() {
        return new AnimatableItem(this);
    }

    @FunctionalInterface
    public interface ControllerCallBack {
        PlayState create(AnimationState<AnimatableItem> state);
    }

    public record ControllerInfo(String name, int transitionTickTime, ControllerCallBack controller) {
    }

    @FunctionalInterface
    public interface AnimationPredicateCallback {
        boolean matches(ServerLevel serverLevel, LivingEntity livingEntity);
    }
}
