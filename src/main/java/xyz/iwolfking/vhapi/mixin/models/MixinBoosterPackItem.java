package xyz.iwolfking.vhapi.mixin.models;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.BoosterPackItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

@Mixin(value = BoosterPackItem.class, remap = false)
public class MixinBoosterPackItem {

    @Inject(method = "loadModels", at = @At("TAIL"))
    public void loadModels(Consumer<ModelResourceLocation> consumer, CallbackInfo ci) {
        Collection<ResourceLocation> packModels = Minecraft.getInstance().getResourceManager().listResources("models/item/booster_pack", s -> s.endsWith(".json"));
        for(ResourceLocation loc : packModels) {
            if (loc.getNamespace().equals("vhapi")) {
                VHAPILoggerUtils.debug("Register custom booster pack model: " + loc);
                consumer.accept(ResourceLocUtils.stripLocationForItemModel(loc));
            }
        }
    }
}
