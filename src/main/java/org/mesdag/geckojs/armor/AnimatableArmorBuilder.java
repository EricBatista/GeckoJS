package org.mesdag.geckojs.armor;

import dev.latvian.mods.kubejs.item.custom.ArmorItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AnimatableArmorBuilder extends ArmorItemBuilder {
    public final ExtendedGeoModel<AnimatableArmorItem> armorModel = new ExtendedGeoModel<>();
    public final transient ArrayList<ControllerCallBack> animations = new ArrayList<>();

    public AnimatableArmorBuilder(ResourceLocation id, ArmorItem.Type type) {
        super(id, type);
    }

    public AnimatableArmorBuilder addController(ControllerCallBack callBack) {
        animations.add(callBack);
        return this;
    }

    public AnimatableArmorBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableArmorItem>> consumer) {
        consumer.accept(armorModel.builder);
        return this;
    }

    public AnimatableArmorBuilder defaultGeoModel() {
        armorModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/block/" + id.getPath() + ".geo.json"));
        armorModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/block/" + id.getPath() + ".png"));
        armorModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/block/" + id.getPath() + ".animation.json"));
        return this;
    }

    @Override
    public Item createObject() {
        return new AnimatableArmorItem(this);
    }

    @FunctionalInterface
    public interface ControllerCallBack {
        PlayState create(AnimationState<AnimatableArmorItem> state);
    }
}
