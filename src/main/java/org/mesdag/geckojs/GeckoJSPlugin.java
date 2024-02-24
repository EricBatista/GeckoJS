package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import org.mesdag.geckojs.armor.AnimatableArmorBuilder;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;
import org.mesdag.geckojs.item.AnimatableItemBuilder;
import org.mesdag.geckojs.item.handheld.AnimatableAxeItem;
import org.mesdag.geckojs.item.handheld.AnimatableHoeItem;
import org.mesdag.geckojs.item.handheld.AnimatablePickaxeItem;
import org.mesdag.geckojs.item.handheld.AnimatableSwordItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.EasingType;
import software.bernie.geckolib.core.animation.RawAnimation;

public class GeckoJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryInfo.BLOCK.addType("animatable", AnimatableBlockBuilder.class, AnimatableBlockBuilder::new);
        RegistryInfo.ITEM.addType("animatable", AnimatableItemBuilder.class, AnimatableItemBuilder::new);
        RegistryInfo.ITEM.addType("anim_helmet", AnimatableArmorBuilder.class, id -> new AnimatableArmorBuilder(id, ArmorItem.Type.HELMET));
        RegistryInfo.ITEM.addType("anim_chestplate", AnimatableArmorBuilder.class, id -> new AnimatableArmorBuilder(id, ArmorItem.Type.CHESTPLATE));
        RegistryInfo.ITEM.addType("anim_leggings", AnimatableArmorBuilder.class, id -> new AnimatableArmorBuilder(id, ArmorItem.Type.LEGGINGS));
        RegistryInfo.ITEM.addType("anim_boots", AnimatableArmorBuilder.class, id -> new AnimatableArmorBuilder(id, ArmorItem.Type.BOOTS));
        RegistryInfo.ITEM.addType("anim_axe", AnimatableAxeItem.Builder.class, AnimatableAxeItem.Builder::new);
        RegistryInfo.ITEM.addType("anim_hoe", AnimatableHoeItem.Builder.class, AnimatableHoeItem.Builder::new);
        RegistryInfo.ITEM.addType("anim_pickaxe", AnimatablePickaxeItem.Builder.class, AnimatablePickaxeItem.Builder::new);
        RegistryInfo.ITEM.addType("anim_sword", AnimatableSwordItem.Builder.class, AnimatableSwordItem.Builder::new);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("RawAnimation", RawAnimation.class);
        event.add("GeoItem", GeoItem.class);
        event.add("EasingType", EasingType.class);
        event.add("DataTickets", DataTickets.class);
        event.add("EquipmentSlot", EquipmentSlot.class);
    }
}
