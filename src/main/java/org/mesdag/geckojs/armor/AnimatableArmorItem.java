package org.mesdag.geckojs.armor;

import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.mesdag.geckojs.ExtendedGeoModel;
import org.mesdag.geckojs.item.AnimatableItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AnimatableArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private final ExtendedGeoModel<AnimatableArmorItem> armorModel;
    private final transient ArrayList<AnimatableArmorBuilder.ControllerCallBack> animations;
    private final transient Multimap<ResourceLocation, AttributeModifier> attributes;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final boolean useGeoModel;

    public AnimatableArmorItem(AnimatableArmorBuilder armorBuilder) {
        super(armorBuilder.armorTier, armorBuilder.armorType, armorBuilder.createItemProperties());
        this.armorModel = armorBuilder.armorModel;
        this.animations = armorBuilder.animations;
        this.attributes = armorBuilder.attributes;
        this.useGeoModel = armorBuilder.useGeoModel;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatableArmorRenderer armorRenderer;
            private AnimatableItemRenderer<AnimatableArmorItem> itemRenderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (armorRenderer == null) {
                    this.armorRenderer = new AnimatableArmorRenderer(armorModel);
                }
                armorRenderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return armorRenderer;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (useGeoModel) {
                    if (itemRenderer == null) {
                        this.itemRenderer = new AnimatableItemRenderer<>(armorModel);
                    }
                    return itemRenderer;
                }
                return IClientItemExtensions.super.getCustomRenderer();
            }
        });
    }

    private boolean modified = false;

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        if (slot == type.getSlot()) {
            Multimap<Attribute, AttributeModifier> defaultAttributes = super.getDefaultAttributeModifiers(slot);
            if (!modified) {
                this.modified = true;
                attributes.forEach((r, m) -> defaultAttributes.put(RegistryInfo.ATTRIBUTE.getValue(r), m));
                this.attributeModifiers = defaultAttributes;
            }
            return attributeModifiers;
        }
        return super.getDefaultAttributeModifiers(slot);
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
