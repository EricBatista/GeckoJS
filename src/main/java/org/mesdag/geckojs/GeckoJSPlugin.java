package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.world.item.ArmorItem;
import org.mesdag.geckojs.armor.AnimatableArmorBuilder;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;
import org.mesdag.geckojs.item.AnimatableItemBuilder;
import software.bernie.geckolib.animatable.GeoItem;
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
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("RawAnimation", RawAnimation.class);
        event.add("GeoItem", GeoItem.class);
    }
}
