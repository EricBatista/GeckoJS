package org.mesdag.geckojs.block;

import org.mesdag.geckojs.ExtendedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AnimatableBlockEntityRenderer extends GeoBlockRenderer<AnimatableBlockEntity> {
    public AnimatableBlockEntityRenderer(ExtendedGeoModel<AnimatableBlockEntity> model) {
        super(model);
        this.scaleWidth = model.builder.scaleWidth;
        this.scaleHeight = model.builder.scaleHeight;
    }

    public AnimatableBlockEntityRenderer(DefaultedBlockGeoModel<AnimatableBlockEntity> model) {
        super(model);
    }
}
