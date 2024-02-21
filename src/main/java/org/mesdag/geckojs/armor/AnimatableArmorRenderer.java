package org.mesdag.geckojs.armor;

import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AnimatableArmorRenderer extends GeoArmorRenderer<AnimatableArmorItem> {
    public AnimatableArmorRenderer(ExtendedGeoModel<AnimatableArmorItem> model) {
        super(model);
        this.scaleWidth = model.builder.scaleWidth;
        this.scaleHeight = model.builder.scaleHeight;
    }
}
