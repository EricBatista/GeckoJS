package org.mesdag.geckojs.block;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.block.entity.BlockEntityBuilder;
import dev.latvian.mods.kubejs.block.entity.BlockEntityInfo;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.GeckoJS;
import org.mesdag.geckojs.block.entity.AnimatableBlockEntity;
import org.mesdag.geckojs.block.entity.AnimatableBlockEntityInfo;

import java.util.Map;
import java.util.function.Consumer;

import static dev.latvian.mods.rhino.mod.util.JsonUtils.GSON;

@SuppressWarnings("unused")
public class AnimatableBlockBuilder extends BlockBuilder {
    private static final JsonObject itemModelJson = new Gson().fromJson("{\"parent\":\"builtin/entity\",\"display\":{\"thirdperson_righthand\":{\"rotation\":[75,45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]},\"thirdperson_lefthand\":{\"rotation\":[75,45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]},\"firstperson_righthand\":{\"rotation\":[0,115,0],\"scale\":[0.4,0.4,0.4]},\"firstperson_lefthand\":{\"rotation\":[0,225,0],\"scale\":[0.4,0.4,0.4]},\"ground\":{\"translation\":[0,3,0],\"scale\":[0.25,0.25,0.25]},\"gui\":{\"rotation\":[30,137,0],\"translation\":[0,-3.75,0],\"scale\":[0.625,0.625,0.625]},\"fixed\":{\"translation\":[0,-1.5,0],\"scale\":[0.5,0.5,0.5]}}}", JsonObject.class);
    public final transient AnimatableBlockEntityInfo blockEntityInfo = new AnimatableBlockEntityInfo(this);
    private final ExtendedGeoModel<AnimatableBlockEntity> blockModel = new ExtendedGeoModel<>();
    private transient AnimatableBlockItemBuilder itemBuilder;

    public AnimatableBlockBuilder(ResourceLocation id) {
        super(id);
        this.itemBuilder = new AnimatableBlockItemBuilder(id, this);
        this.opaque = false;
    }

    @Info("Creates a animatable Block Entity for this block")
    public AnimatableBlockBuilder animatableBlockEntity(Consumer<AnimatableBlockEntityInfo> consumer) {
        consumer.accept(blockEntityInfo);
        return this;
    }

    public AnimatableBlockBuilder geoModel(Consumer<ExtendedGeoModel.Builder<AnimatableBlockEntity>> consumer) {
        consumer.accept(blockModel.builder);
        return this;
    }

    public AnimatableBlockBuilder defaultGeoModel() {
        blockModel.builder.setSimpleModel(new ResourceLocation(id.getNamespace(), "geo/block/" + id.getPath() + ".geo.json"));
        blockModel.builder.setSimpleTexture(new ResourceLocation(id.getNamespace(), "textures/block/" + id.getPath() + ".png"));
        blockModel.builder.setSimpleAnimation(new ResourceLocation(id.getNamespace(), "animations/block/" + id.getPath() + ".animation.json"));
        return this;
    }

    public AnimatableBlockBuilder animatableItem(Consumer<AnimatableBlockItemBuilder> consumer) {
        consumer.accept(itemBuilder);
        return this;
    }

    @Override
    public BlockBuilder noItem() {
        this.itemBuilder = null;
        return super.noItem();
    }

    @Override
    public void createAdditionalObjects() {
        if (itemBuilder != null) {
            RegistryInfo.ITEM.addBuilder(itemBuilder.hasModel ? itemBuilder : itemBuilder.defaultGeoModel());
        }
        RegistryInfo.BLOCK_ENTITY_TYPE.addBuilder(new BlockEntityBuilder(id, blockEntityInfo));
        GeckoJS.REGISTERED_BLOCK.put(id, blockModel);
    }

    @Override
    public Block createObject() {
        return new AnimatableBlock(this);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        generator.blockState(id, this::generateBlockStateJson);
        JsonObject blockModelJson = GSON.toJsonTree(Map.of(
            "parent", "block/block",
            "textures", Map.of("particle", id.getNamespace() + ":block/" + id.getPath())
        )).getAsJsonObject();
        generator.json(new ResourceLocation(id.getNamespace(), "models/block/" + id.getPath()), blockModelJson);
        if (itemBuilder != null) {
            generator.json(AssetJsonGenerator.asItemModelLocation(id), itemModelJson);
        }
    }

    @HideFromJS
    @Override
    public BlockBuilder blockEntity(Consumer<BlockEntityInfo> callback) {
        return super.blockEntity(callback);
    }

    @HideFromJS
    @Override
    public BlockBuilder item(@Nullable Consumer<BlockItemBuilder> i) {
        return super.item(i);
    }

    @HideFromJS
    @Override
    public BlockBuilder renderType(String l) {
        return super.renderType(l);
    }

    @HideFromJS
    @Override
    public BlockBuilder texture(String id, String tex) {
        return super.texture(id, tex);
    }

    @HideFromJS
    @Override
    public BlockBuilder textureAll(String tex) {
        return super.textureAll(tex);
    }

    @HideFromJS
    @Override
    public BlockBuilder textureSide(Direction direction, String tex) {
        return super.textureSide(direction, tex);
    }

    @HideFromJS
    @Override
    public BlockBuilder model(String m) {
        return super.model(m);
    }

    @HideFromJS
    @Override
    public BlockBuilder transparent(boolean b) {
        return super.transparent(b);
    }

    @HideFromJS
    @Override
    public BlockBuilder defaultCutout() {
        return super.defaultCutout();
    }

    @HideFromJS
    @Override
    public BlockBuilder defaultTranslucent() {
        return super.defaultTranslucent();
    }
}
