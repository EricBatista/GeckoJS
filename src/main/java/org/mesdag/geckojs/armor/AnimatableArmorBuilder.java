package org.mesdag.geckojs.armor;

import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.custom.ArmorItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.item.AnimatableItemBuilder;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AnimatableArmorBuilder extends ArmorItemBuilder {
    public final ExtendedGeoModel<AnimatableArmorItem> armorModel = new ExtendedGeoModel<>();
    public final transient ArrayList<ControllerCallBack> animations = new ArrayList<>();
    public transient boolean useGeoModel = false;

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

    @Override
    public Item createObject() {
        return new AnimatableArmorItem(this);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        if (useGeoModel) {
            generator.json(AssetJsonGenerator.asItemModelLocation(id), AnimatableItemBuilder.itemModelJson);
        } else {
            super.generateAssetJsons(generator);
        }
    }

    @FunctionalInterface
    public interface ControllerCallBack {
        PlayState create(AnimationState<AnimatableArmorItem> state);
    }
}
