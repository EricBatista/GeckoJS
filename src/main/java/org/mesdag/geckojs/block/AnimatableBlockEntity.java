package org.mesdag.geckojs.block;

import dev.latvian.mods.kubejs.block.entity.BlockEntityJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;

public class AnimatableBlockEntity extends BlockEntityJS implements GeoBlockEntity {
    private final ArrayList<AnimatableBlockEntityInfo.ControllerCallBack> animations;
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

    public AnimatableBlockEntity(BlockPos blockPos, BlockState blockState, AnimatableBlockEntityInfo entityInfo) {
        super(blockPos, blockState, entityInfo);
        this.animations = entityInfo.animations;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        animations.forEach(animation -> controllers.add(new AnimationController<>(this, animation::create)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
