package org.mesdag.geckojs.item;

import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatableItemRenderer extends GeoItemRenderer<AnimatableItem> {
    public AnimatableItemRenderer(ExtendedGeoModel<AnimatableItem> model) {
        super(model);
        this.scaleWidth = model.builder.scaleWidth;
        this.scaleHeight = model.builder.scaleHeight;
    }
}
