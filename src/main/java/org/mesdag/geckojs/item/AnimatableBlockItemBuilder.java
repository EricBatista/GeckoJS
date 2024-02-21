package org.mesdag.geckojs.item;

import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AnimatableBlockItemBuilder extends ItemBuilder {
    private final AnimatableBlockBuilder blockBuilder;
    private final ExtendedGeoModel<AnimatableBlockItem> itemModel = new ExtendedGeoModel<>();
    public boolean hasModel = false;

    public AnimatableBlockItemBuilder(ResourceLocation id, AnimatableBlockBuilder blockBuilder) {
        super(id);
        this.blockBuilder = blockBuilder;
    }

    public AnimatableBlockItemBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableBlockItem>> consumer) {
        consumer.accept(itemModel.builder);
        this.hasModel = true;
        return this;
    }

    public AnimatableBlockItemBuilder defaultGeoModel() {
        itemModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/block/" + id.getPath() + ".geo.json"));
        itemModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/block/" + id.getPath() + ".png"));
        itemModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/block/" + id.getPath() + ".animation.json"));
        this.hasModel = true;
        return this;
    }

    @Override
    public Item createObject() {
        return new AnimatableBlockItem(blockBuilder.get(), createItemProperties(), itemModel);
    }

    @Override
    public String getTranslationKeyGroup() {
        return "block";
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
    }
}
