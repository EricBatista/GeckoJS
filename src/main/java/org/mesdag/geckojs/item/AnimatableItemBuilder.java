package org.mesdag.geckojs.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.mesdag.geckojs.AnimationControllerBuilder;
import org.mesdag.geckojs.ExtendedGeoModel;

import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AnimatableItemBuilder extends ItemBuilder {
    public final ExtendedGeoModel<AnimatableItem> itemModel = new ExtendedGeoModel<>();
    public UseAnimationCallback useAnimationCallback;
    public FinishUsingAnimationCallback finishUsingAnimationCallback;
    public ReleaseUsingAnimationCallback releaseUsingAnimationCallback;
    public final transient ArrayList<AnimationControllerBuilder<AnimatableItem>> controllers = new ArrayList<>();

    public AnimatableItemBuilder(ResourceLocation id) {
        super(id);
    }

    public AnimatableItemBuilder useAnimation(UseAnimationCallback callback) {
        this.useAnimationCallback = callback;
        return this;
    }

    public AnimatableItemBuilder finishUsingAnimation(FinishUsingAnimationCallback callback) {
        this.finishUsingAnimationCallback = callback;
        return this;
    }

    public AnimatableItemBuilder releaseUsingAnimation(ReleaseUsingAnimationCallback callback) {
        this.releaseUsingAnimationCallback = callback;
        return this;
    }

    public AnimatableItemBuilder addController(Consumer<AnimationControllerBuilder<AnimatableItem>> consumer) {
        AnimationControllerBuilder<AnimatableItem> builder = new AnimationControllerBuilder<>();
        consumer.accept(builder);
        controllers.add(builder);
        return this;
    }

    public AnimatableItemBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableItem>> consumer) {
        consumer.accept(itemModel.builder);
        return this;
    }

    public AnimatableItemBuilder defaultGeoModel() {
        itemModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/block/" + id.getPath() + ".geo.json"));
        itemModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/block/" + id.getPath() + ".png"));
        itemModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/block/" + id.getPath() + ".animation.json"));
        return this;
    }

    @Override
    public AnimatableItem createObject() {
        return new AnimatableItem(this);
    }

    @FunctionalInterface
    public interface UseAnimationCallback {
        void call(AnimatableItem self, ServerLevel serverLevel, Player player, InteractionHand hand);
    }

    @FunctionalInterface
    public interface FinishUsingAnimationCallback {
        void call(AnimatableItem self, ServerLevel serverLevel, LivingEntity livingEntity);
    }

    @FunctionalInterface
    public interface ReleaseUsingAnimationCallback {
        void call(AnimatableItem self, ServerLevel serverLevel, LivingEntity livingEntity, int tick);
    }
}
