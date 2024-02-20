package org.mesdag.geckojs;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("unused")
public class ExtendedGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    static final ResourceLocation EMPTY = new ResourceLocation("geckojs", "empty");
    public final Builder<T> builder = new Builder<>();

    @Override
    public ResourceLocation getModelResource(T animatable) {
        if (builder.model == null) return EMPTY;
        return builder.model.create(animatable);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (builder.texture == null) return EMPTY;
        return builder.texture.create(animatable);
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (builder.animation == null) return EMPTY;
        return builder.animation.create(animatable);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    public static class Builder<T extends GeoAnimatable> {
        ResourceCallback<T> model;
        ResourceCallback<T> texture;
        ResourceCallback<T> animation;
        public float scaleWidth = 1.0F;
        public float scaleHeight = 1.0F;

        public void setModel(ResourceCallback<T> model) {
            this.model = model;
        }

        public void setTexture(ResourceCallback<T> texture) {
            this.texture = texture;
        }

        public void setAnimation(ResourceCallback<T> animation) {
            this.animation = animation;
        }

        public void setScale(float scale) {
            this.scaleWidth = scale;
            this.scaleHeight = scale;
        }
    }

    @FunctionalInterface
    public interface ResourceCallback<T extends GeoAnimatable> {
        ResourceLocation create(T animatable);
    }
}
