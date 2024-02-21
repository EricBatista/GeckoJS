package org.mesdag.geckojs.armor;

import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AnimatableArmorRenderer extends GeoArmorRenderer<AnimatableArmorItem> {
    public AnimatableArmorRenderer(ExtendedGeoModel<AnimatableArmorItem> model) {
        super(model);
        this.scaleWidth = model.builder.scaleWidth;
        this.scaleHeight = model.builder.scaleHeight;
        if (model.builder.autoGlowing) {
            addRenderLayer(new AutoGlowingGeoLayer<>(this));
        }
    }
}
