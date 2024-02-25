package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mesdag.geckojs.block.entity.AnimatableBlockEntity;
import org.mesdag.geckojs.block.entity.AnimatableBlockEntityRenderer;
import software.bernie.geckolib.GeckoLib;

import java.util.HashSet;
import java.util.Hashtable;

@Mod("geckojs")
public class GeckoJS {
    public static final Hashtable<ResourceLocation, ExtendedGeoModel<AnimatableBlockEntity>> REGISTERED_BLOCK = new Hashtable<>();
    public static final HashSet<ResourceLocation> REGISTERED_SHIELD = new HashSet<>();

    public GeckoJS() {
        GeckoLib.initialize();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(GeckoJS::registerRenderers);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GeckoJSClient::new);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        REGISTERED_BLOCK.forEach((id, model) -> event.registerBlockEntityRenderer(RegistryInfo.BLOCK_ENTITY_TYPE.getValue(id), context -> new AnimatableBlockEntityRenderer(model)));
    }
}
