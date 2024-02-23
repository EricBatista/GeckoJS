package org.mesdag.geckojs.item.handheld;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.custom.HandheldItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import org.mesdag.geckojs.AnimationControllerBuilder;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.item.AnimatableItemBuilder;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public abstract class AnimatableHandheldItemBuilder<T extends TieredItem & GeoItem> extends HandheldItemBuilder {
    public final ExtendedGeoModel<T> itemModel = new ExtendedGeoModel<>();
    public UseAnimationCallback<T> useAnimationCallback;
    public FinishUsingAnimationCallback<T> finishUsingAnimationCallback;
    public ReleaseUsingAnimationCallback<T> releaseUsingAnimationCallback;
    public final transient ArrayList<AnimationControllerBuilder<T>> controllers = new ArrayList<>();
    public transient boolean useEntityGuiLighting = false;

    public AnimatableHandheldItemBuilder(ResourceLocation id, float attackDamageBaseline, float speedBaseline) {
        super(id, attackDamageBaseline, speedBaseline);
    }

    public AnimatableHandheldItemBuilder<T> useAnimation(UseAnimationCallback<T> callback) {
        this.useAnimationCallback = callback;
        return this;
    }

    public AnimatableHandheldItemBuilder<T> finishUsingAnimation(FinishUsingAnimationCallback<T> callback) {
        this.finishUsingAnimationCallback = callback;
        return this;
    }

    public AnimatableHandheldItemBuilder<T> releaseUsingAnimation(ReleaseUsingAnimationCallback<T> callback) {
        this.releaseUsingAnimationCallback = callback;
        return this;
    }

    public AnimatableHandheldItemBuilder<T> addController(Consumer<AnimationControllerBuilder<T>> consumer) {
        AnimationControllerBuilder<T> builder = new AnimationControllerBuilder<>();
        consumer.accept(builder);
        controllers.add(builder);
        return this;
    }

    public AnimatableHandheldItemBuilder<T> geoModel(Consumer<ExtendedGeoModel.Builder<T>> consumer) {
        consumer.accept(itemModel.builder);
        return this;
    }

    public AnimatableHandheldItemBuilder<T> defaultGeoModel() {
        itemModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/item/" + id.getPath() + ".geo.json"));
        itemModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/item/" + id.getPath() + ".png"));
        itemModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/item/" + id.getPath() + ".animation.json"));
        return this;
    }

    public AnimatableHandheldItemBuilder<T> useEntityGuiLighting() {
        this.useEntityGuiLighting = true;
        return this;
    }

    @HideFromJS
    @Override
    public ItemBuilder modelJson(JsonObject json) {
        return super.modelJson(json);
    }

    @HideFromJS
    @Override
    public ItemBuilder parentModel(String m) {
        return super.parentModel(m);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        generator.json(AssetJsonGenerator.asItemModelLocation(id), AnimatableItemBuilder.itemModelJson);
    }

    @FunctionalInterface
    public interface UseAnimationCallback<T extends TieredItem & GeoItem> {
        void call(T self, ServerLevel serverLevel, Player player, InteractionHand hand);
    }

    @FunctionalInterface
    public interface FinishUsingAnimationCallback<T extends TieredItem & GeoItem> {
        void call(T self, ServerLevel serverLevel, LivingEntity livingEntity);
    }

    @FunctionalInterface
    public interface ReleaseUsingAnimationCallback<T extends TieredItem & GeoItem> {
        void call(T self, ServerLevel serverLevel, LivingEntity livingEntity, int tick);
    }
}
