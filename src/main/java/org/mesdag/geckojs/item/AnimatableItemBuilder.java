package org.mesdag.geckojs.item;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
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
    public static final JsonObject itemModelJson = new Gson().fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"rotation\":[75,45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]},\"thirdperson_lefthand\":{\"rotation\":[75,45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]},\"firstperson_righthand\":{\"rotation\":[0,115,0],\"scale\":[0.4,0.4,0.4]},\"firstperson_lefthand\":{\"rotation\":[0,225,0],\"scale\":[0.4,0.4,0.4]},\"ground\":{\"translation\":[0,3,0],\"scale\":[0.25,0.25,0.25]},\"gui\":{\"rotation\":[30,137,0],\"translation\":[0,-3.75,0],\"scale\":[0.625,0.625,0.625]},\"fixed\":{\"translation\":[0,-1.5,0],\"scale\":[0.5,0.5,0.5]}}}", JsonObject.class);
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
        itemModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/item/" + id.getPath() + ".geo.json"));
        itemModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/item/" + id.getPath() + ".png"));
        itemModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/item/" + id.getPath() + ".animation.json"));
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
    public AnimatableItem createObject() {
        return new AnimatableItem(this);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        generator.json(AssetJsonGenerator.asItemModelLocation(id), itemModelJson);
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
