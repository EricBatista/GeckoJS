package org.mesdag.geckojs;

import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class GeckoJSClient {
    public GeckoJSClient() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> GeckoJS.REGISTERED_SHIELD.forEach(id -> ItemProperties.register(
            RegistryInfo.ITEM.getValue(id),
            new ResourceLocation("blocking"),
            (stack, world, living, itemId) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F
            )));
        //REGISTERED_BLOCK.keySet().forEach(id -> ItemBlockRenderTypes.setRenderLayer(RegistryInfo.BLOCK.getValue(id), RenderType.translucent()));
    }
}
