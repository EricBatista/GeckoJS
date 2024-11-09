package org.mesdag.geckojs;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.mesdag.geckojs.block.entity.AnimatableBlockEntity;
import software.bernie.geckolib.GeckoLib;

import java.util.HashSet;
import java.util.Hashtable;

@Mod(GeckoJS.MOD_ID)
public class GeckoJS {
    public static final String MOD_ID = "geckojs";
    public static final Hashtable<ResourceLocation, ExtendedGeoModel<AnimatableBlockEntity>> REGISTERED_BLOCK = new Hashtable<>();
    public static final HashSet<ResourceLocation> REGISTERED_SHIELD = new HashSet<>();

    public GeckoJS(){
        GeckoLib.initialize();
    }
}
