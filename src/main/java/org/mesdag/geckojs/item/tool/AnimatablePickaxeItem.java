package org.mesdag.geckojs.item.tool;

import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.mesdag.geckojs.item.AnimatableItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class AnimatablePickaxeItem extends PickaxeItem implements GeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private final Builder pickaxeItemBuilder;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AnimatablePickaxeItem(Builder pickaxeItemBuilder) {
        super(pickaxeItemBuilder.toolTier, (int) pickaxeItemBuilder.attackDamageBaseline, pickaxeItemBuilder.speedBaseline, pickaxeItemBuilder.createItemProperties());
        this.pickaxeItemBuilder = pickaxeItemBuilder;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel && pickaxeItemBuilder.usingAnimationCallback != null) {
            pickaxeItemBuilder.usingAnimationCallback.call(this, serverLevel, (ServerPlayer) player, hand);
        }
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (level instanceof ServerLevel serverLevel && pickaxeItemBuilder.finishUsingAnimationCallback != null) {
            pickaxeItemBuilder.finishUsingAnimationCallback.call(this, serverLevel, livingEntity);
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity, int tick) {
        if (level instanceof ServerLevel serverLevel && pickaxeItemBuilder.releaseUsingAnimationCallback != null) {
            pickaxeItemBuilder.releaseUsingAnimationCallback.call(this, serverLevel, livingEntity, tick);
        }
        super.releaseUsing(itemStack, level, livingEntity, tick);
    }

    private boolean modified = false;

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            if (!modified) {
                this.modified = true;
                Multimap<Attribute, AttributeModifier> defaultModifiers = super.getDefaultAttributeModifiers(equipmentSlot);
                pickaxeItemBuilder.attributes.forEach((r, m) -> defaultModifiers.put(RegistryInfo.ATTRIBUTE.getValue(r), m));
                this.attributeModifiers = defaultModifiers;
            }
            return attributeModifiers;
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatableItemRenderer<AnimatablePickaxeItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    AnimatableItemRenderer<AnimatablePickaxeItem> itemRenderer = new AnimatableItemRenderer<>(pickaxeItemBuilder.itemModel);
                    if (pickaxeItemBuilder.useEntityGuiLighting) itemRenderer.useAlternateGuiLighting();
                    this.renderer = itemRenderer;
                }
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        pickaxeItemBuilder.controllers.forEach(controller -> registrar.add(controller.build(this)));
        pickaxeItemBuilder.animations.forEach(animation -> registrar.add(new AnimationController<>(this, animation::create)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }

    public static class Builder extends AnimatableTiredItemBuilder<AnimatablePickaxeItem> {
        public Builder(ResourceLocation id) {
            super(id, 1F, -2.8F);
        }

        @Override
        public AnimatablePickaxeItem createObject() {
            return new AnimatablePickaxeItem(this);
        }
    }
}
