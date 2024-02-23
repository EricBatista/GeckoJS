package org.mesdag.geckojs.armor;

import net.minecraft.world.entity.EquipmentSlot;
import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AnimatableArmorRenderer extends GeoArmorRenderer<AnimatableArmorItem> {
    private final AnimatableArmorBuilder.VisibilityCallback visibilityCallback;

    public AnimatableArmorRenderer(ExtendedGeoModel<AnimatableArmorItem> model, AnimatableArmorBuilder.VisibilityCallback visibilityCallback) {
        super(model);
        this.scaleWidth = model.builder.scaleWidth;
        this.scaleHeight = model.builder.scaleHeight;
        this.visibilityCallback = visibilityCallback;
        if (model.builder.autoGlowing) {
            addRenderLayer(new AutoGlowingGeoLayer<>(this));
        }
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        if (visibilityCallback == null) {
            super.applyBoneVisibilityBySlot(currentSlot);
        } else {
            visibilityCallback.apply(this, currentSlot);
        }
    }
}
