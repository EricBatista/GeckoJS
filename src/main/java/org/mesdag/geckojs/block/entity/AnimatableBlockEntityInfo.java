package org.mesdag.geckojs.block.entity;

import dev.latvian.mods.kubejs.block.entity.BlockEntityInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AnimatableBlockEntityInfo extends BlockEntityInfo {
    public final transient ArrayList<ControllerCallBack> animations = new ArrayList<>();

    public AnimatableBlockEntityInfo(AnimatableBlockBuilder blockBuilder) {
        super(blockBuilder);
    }

    public AnimatableBlockEntityInfo addController(ControllerCallBack callBack) {
        animations.add(callBack);
        return this;
    }

    @Override
    public AnimatableBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnimatableBlockEntity(pos, state, this);
    }

    @FunctionalInterface
    public interface ControllerCallBack {
        PlayState create(AnimationState<AnimatableBlockEntity> state);
    }
}
