package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mesdag.geckojs.block.AnimatableBlockEntity;
import org.mesdag.geckojs.block.AnimatableBlockEntityRenderer;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

import java.util.Hashtable;

@Mod("geckojs")
public class GeckoJS {
    public static final Hashtable<ResourceLocation, ExtendedGeoModel<AnimatableBlockEntity>> REGISTERED = new Hashtable<>();
    public static final Hashtable<ResourceLocation, DefaultedBlockGeoModel<AnimatableBlockEntity>> DEFAULTED = new Hashtable<>();

    public GeckoJS() {
        GeckoLib.initialize();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(GeckoJS::registerRenderers);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        REGISTERED.forEach((id, model) -> event.registerBlockEntityRenderer(RegistryInfo.BLOCK_ENTITY_TYPE.getValue(id), context -> new AnimatableBlockEntityRenderer(model)));
        DEFAULTED.forEach((id, model) -> event.registerBlockEntityRenderer(RegistryInfo.BLOCK_ENTITY_TYPE.getValue(id), context -> new AnimatableBlockEntityRenderer(model)));
    }
}
