package org.mesdag.geckojs.block;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.item.AnimatableItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class AnimatableBlockItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private final ExtendedGeoModel<AnimatableBlockItem> model;

    public AnimatableBlockItem(Block block, Properties properties, ExtendedGeoModel<AnimatableBlockItem> model) {
        super(block, properties);
        this.model = model;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatableItemRenderer<AnimatableBlockItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new AnimatableItemRenderer<>(model);
                }
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
