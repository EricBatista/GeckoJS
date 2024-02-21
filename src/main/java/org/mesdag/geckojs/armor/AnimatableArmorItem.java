package org.mesdag.geckojs.armor;

import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.model.HumanoidModel;
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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AnimatableArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private final ExtendedGeoModel<AnimatableArmorItem> armorModel;
    private final transient ArrayList<AnimatableArmorBuilder.ControllerCallBack> animations;
    private final transient Multimap<ResourceLocation, AttributeModifier> attributes;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AnimatableArmorItem(AnimatableArmorBuilder armorBuilder) {
        super(armorBuilder.armorTier, armorBuilder.armorType, armorBuilder.createItemProperties());
        this.armorModel = armorBuilder.armorModel;
        this.animations = armorBuilder.animations;
        this.attributes = armorBuilder.attributes;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    this.renderer = new AnimatableArmorRenderer(armorModel);
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    private boolean modified = false;

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        if (!modified) {
            this.modified = true;
            Multimap<Attribute, AttributeModifier> defaultAttributes = super.getDefaultAttributeModifiers(slot);
            if (slot == type.getSlot()) {
                attributes.forEach((r, m) -> defaultAttributes.put(RegistryInfo.ATTRIBUTE.getValue(r), m));
            }
            this.attributeModifiers = defaultAttributes;
            return attributeModifiers;
        }
        return attributeModifiers;
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
