package org.mesdag.geckojs.armor;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.custom.ArmorItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.mesdag.geckojs.AnimationControllerBuilder;
import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.ArrayList;
import java.util.function.Consumer;

import static dev.latvian.mods.rhino.mod.util.JsonUtils.GSON;

@SuppressWarnings("unused")
public class AnimatableArmorBuilder extends ArmorItemBuilder {
    private static final JsonObject helmetModel = GSON.fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"translation\":[0,-9,1],\"scale\":[0.55,0.55,0.55]},\"thirdperson_lefthand\":{\"translation\":[0,-9,1],\"scale\":[0.55,0.55,0.55]},\"firstperson_righthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,-6.8,4],\"scale\":[0.68,0.68,0.68]},\"firstperson_lefthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,-6.8,4],\"scale\":[0.68,0.68,0.68]},\"ground\":{\"translation\":[0,-9,0],\"scale\":[0.5,0.5,0.5]},\"gui\":{\"rotation\":[20,-20,0],\"translation\":[0,-20,0]},\"fixed\":{\"rotation\":[0,180,0],\"translation\":[0,-20,0]}}}", JsonObject.class);
    private static final JsonObject chestplateModel = GSON.fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"translation\":[0,-4,1],\"scale\":[0.55,0.55,0.55]},\"thirdperson_lefthand\":{\"translation\":[0,-4,1],\"scale\":[0.55,0.55,0.55]},\"firstperson_righthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,-4.8,2],\"scale\":[0.68,0.68,0.68]},\"firstperson_lefthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,-4.8,2],\"scale\":[0.68,0.68,0.68]},\"ground\":{\"translation\":[0,-5,0],\"scale\":[0.5,0.5,0.5]},\"gui\":{\"rotation\":[20,-20,0],\"translation\":[0,-10,0]},\"fixed\":{\"rotation\":[0,180,0],\"translation\":[0,-10,0]}}}", JsonObject.class);
    private static final JsonObject leggingsModel = GSON.fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"translation\":[0,2,1],\"scale\":[0.55,0.55,0.55]},\"thirdperson_lefthand\":{\"translation\":[0,2,1],\"scale\":[0.55,0.55,0.55]},\"firstperson_righthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,4.2,0],\"scale\":[0.68,0.68,0.68]},\"firstperson_lefthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,4.2,0],\"scale\":[0.68,0.68,0.68]},\"ground\":{\"translation\":[0,0.5,0],\"scale\":[0.5,0.5,0.5]},\"gui\":{\"rotation\":[20,-20,0]},\"fixed\":{\"rotation\":[0,180,0],\"translation\":[0,2,0]}}}", JsonObject.class);
    private static final JsonObject bootsModel = GSON.fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"translation\":[0,3.5,1],\"scale\":[0.55,0.55,0.55]},\"thirdperson_lefthand\":{\"translation\":[0,3.5,1],\"scale\":[0.55,0.55,0.55]},\"firstperson_righthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,6.7,-1.5],\"scale\":[0.68,0.68,0.68]},\"firstperson_lefthand\":{\"rotation\":[0,-90,25],\"translation\":[1.13,6.7,-1.5],\"scale\":[0.68,0.68,0.68]},\"ground\":{\"translation\":[0,2,0],\"scale\":[0.5,0.5,0.5]},\"gui\":{\"rotation\":[20,-20,0],\"translation\":[0,5,0]},\"fixed\":{\"rotation\":[0,180,0],\"translation\":[0,6,0]}}}", JsonObject.class);
    public final ExtendedGeoModel<AnimatableArmorItem> armorModel = new ExtendedGeoModel<>();
    public final transient ArrayList<AnimationControllerBuilder<AnimatableArmorItem>> controllers = new ArrayList<>();
    public final transient ArrayList<AnimationCallback> animations = new ArrayList<>();
    public boolean useGeoModel = false;
    public VisibilityCallback visibilityCallback;

    public AnimatableArmorBuilder(ResourceLocation id, ArmorItem.Type type) {
        super(id, type);
    }

    public AnimatableArmorBuilder addAnimation(AnimationCallback callBack) {
        animations.add(callBack);
        return this;
    }

    public AnimatableArmorBuilder addController(Consumer<AnimationControllerBuilder<AnimatableArmorItem>> consumer) {
        AnimationControllerBuilder<AnimatableArmorItem> builder = new AnimationControllerBuilder<>();
        consumer.accept(builder);
        controllers.add(builder);
        return this;
    }

    public AnimatableArmorBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableArmorItem>> consumer) {
        consumer.accept(armorModel.builder);
        return this;
    }

    public AnimatableArmorBuilder armorItemUseGeoModel() {
        this.useGeoModel = true;
        return this;
    }

    public AnimatableArmorBuilder defaultGeoModel() {
        armorModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/armor/" + id.getPath() + ".geo.json"));
        armorModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/armor/" + id.getPath() + ".png"));
        armorModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/armor/" + id.getPath() + ".animation.json"));
        return this;
    }

    public AnimatableArmorBuilder boneVisibility(VisibilityCallback callback) {
        this.visibilityCallback = callback;
        return this;
    }

    @Override
    public Item createObject() {
        return new AnimatableArmorItem(this);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        if (useGeoModel) {
            ResourceLocation path = AssetJsonGenerator.asItemModelLocation(id);
            switch (armorType) {
                case HELMET -> generator.json(path, helmetModel);
                case CHESTPLATE -> generator.json(path, chestplateModel);
                case LEGGINGS -> generator.json(path, leggingsModel);
                case BOOTS -> generator.json(path, bootsModel);
            }
        } else {
            super.generateAssetJsons(generator);
        }
    }

    @FunctionalInterface
    public interface AnimationCallback {
        PlayState create(AnimationState<AnimatableArmorItem> state);
    }

    @FunctionalInterface
    public interface VisibilityCallback {
        void apply(GeoArmorRenderer<AnimatableArmorItem> renderer, EquipmentSlot slot);
    }
}
