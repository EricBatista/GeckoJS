package org.mesdag.geckojs.item;

import dev.latvian.mods.kubejs.item.custom.BasicItemJS;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class AnimatableItem extends BasicItemJS implements GeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private final AnimatableItemBuilder itemBuilder;

    public AnimatableItem(AnimatableItemBuilder builder) {
        super(builder);
        this.itemBuilder = builder;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel) {
            itemBuilder.useTriggers.forEach(((predicate, path) -> {
                if (predicate.matches(serverLevel, player)) {
                    triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), path.getA(), path.getB());
                }
            }));
        }
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (level instanceof ServerLevel serverLevel) {
            itemBuilder.finishUsingTriggers.forEach(((predicate, path) -> {
                if (predicate.matches(serverLevel, livingEntity)) {
                    triggerAnim(livingEntity, GeoItem.getOrAssignId(itemStack, serverLevel), path.getA(), path.getB());
                }
            }));
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity, int tick) {
        if (level instanceof ServerLevel serverLevel) {
            itemBuilder.releaseUsingTriggers.forEach(((predicate, path) -> {
                if (predicate.matches(serverLevel, livingEntity)) {
                    triggerAnim(livingEntity, GeoItem.getOrAssignId(itemStack, serverLevel), path.getA(), path.getB());
                }
            }));
        }
        super.releaseUsing(itemStack, level, livingEntity, tick);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final AnimatableItemRenderer renderer = new AnimatableItemRenderer(itemBuilder.itemModel);

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        itemBuilder.animations.forEach((info, triggers) -> {
            AnimationController<AnimatableItem> controller = new AnimationController<>(this, info.name(), info.transitionTickTime(), info.controller()::create);
            if (triggers != null) {
                triggers.forEach(controller::triggerableAnim);
            }
            controllers.add(controller);
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
