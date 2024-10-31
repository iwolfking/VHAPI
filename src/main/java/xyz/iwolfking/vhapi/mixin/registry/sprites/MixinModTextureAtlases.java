package xyz.iwolfking.vhapi.mixin.registry.sprites;

import iskallia.vault.init.ModTextureAtlases;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.VHAPI;

import java.util.List;

@Mixin(value = ModTextureAtlases.class, remap = false)
public class MixinModTextureAtlases {
    /**
     * @author iwolfking
     * @reason Modify texture atlas to contain all modifier textures under /gui/modifiers
     */
    @Inject(method = "lambda$static$0", at = @At("HEAD"), cancellable = true)
    private static void lambda$static$0(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        if(!VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("MODIFIERS").isEmpty()) {
            cir.setReturnValue(VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("MODIFIERS"));
        }
    }

    /**
     * @author iwolfking
     * @reason Modify texture atlas to contain all ability textures under /gui/abilities
     */
    @Inject(method = "lambda$static$1", at = @At("HEAD"), cancellable = true)
    private static void lambda$static$1(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        if(!VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("ABILITY").isEmpty()) {
            cir.setReturnValue(VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("ABILITY"));
        }
    }

    //SKIP 2: Archetypes

    /**
     * @author iwolfking
     * @reason Modify texture atlas to contain all ability textures under /gui/abilities
     */
    @Inject(method = "lambda$static$3", at = @At("HEAD"), cancellable = true)
    private static void lambda$static$3(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        if(!VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("RESEARCH").isEmpty()) {
            cir.setReturnValue(VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("RESEARCH"));
        }
    }

    /**
     * @author iwolfking
     * @reason Modify texture atlas to contain all ability textures under /gui/abilities
     */
    @Inject(method = "lambda$static$4", at = @At("HEAD"), cancellable = true)
    private static void lambda$static$4(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        if(!VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("RESEARCH_GROUP").isEmpty()) {
            cir.setReturnValue(VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("RESEARCH_GROUP"));
        }
    }

    /**
     * @author iwolfking
     * @reason Modify texture atlas to contain all ability textures under /gui/abilities
     */
    @Inject(method = "lambda$static$5", at = @At("HEAD"), cancellable = true)
    private static void lambda$static$5(CallbackInfoReturnable<List<ResourceLocation>> cir) {
        if(!VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("SKILLS").isEmpty()) {
            cir.setReturnValue(VHAPI.Client.CUSTOM_TEXTURE_ATLAS_MAP.get("SKILLS"));
        }
    }

    //SKIP 6: SCREEN
    //SKIP 7: SLOT
    //SKIP 8: SCAVENGER
    //SKIP 9: MOB HEADS
    //SKIP 10: QUESTS
    //SKIP 11: ACHIEVEMENTS
}
