package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mesdag.geckojs.block.AnimatableBlockEntity;
import org.mesdag.geckojs.block.AnimatableBlockEntityRenderer;
import software.bernie.example.GeckoLibMod;
import software.bernie.example.registry.BlockRegistry;
import software.bernie.geckolib.GeckoLib;

import java.util.Hashtable;

@Mod("geckojs")
public class GeckoJS {
    public static final Hashtable<ResourceLocation, ExtendedGeoModel<AnimatableBlockEntity>> REGISTERED_BLOCK = new Hashtable<>();

    public GeckoJS() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        GeckoLib.initialize();
        bus.addListener(GeckoJS::registerRenderers);
        bus.addListener(GeckoJS::registerItemRenderers);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        REGISTERED_BLOCK.forEach((id, model) -> event.registerBlockEntityRenderer(RegistryInfo.BLOCK_ENTITY_TYPE.getValue(id), context -> new AnimatableBlockEntityRenderer(model)));
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void registerItemRenderers(final FMLClientSetupEvent event) {
        REGISTERED_BLOCK.keySet().forEach(id -> ItemBlockRenderTypes.setRenderLayer(RegistryInfo.BLOCK.getValue(id), RenderType.translucent()));
    }
}
