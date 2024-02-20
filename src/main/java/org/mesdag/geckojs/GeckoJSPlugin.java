package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import org.mesdag.geckojs.block.AnimatableBlockBuilder;
import org.mesdag.geckojs.item.AnimatableItemBuilder;
import software.bernie.geckolib.core.animation.RawAnimation;

public class GeckoJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryInfo.BLOCK.addType("animatable", AnimatableBlockBuilder.class, AnimatableBlockBuilder::new);
        RegistryInfo.ITEM.addType("animatable", AnimatableItemBuilder.class, AnimatableItemBuilder::new);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("RawAnimation", RawAnimation.class);
    }
}
