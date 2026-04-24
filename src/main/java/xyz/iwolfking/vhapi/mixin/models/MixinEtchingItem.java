package xyz.iwolfking.vhapi.mixin.models;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.VaultMod;
import iskallia.vault.config.gear.VaultEtchingConfig;
import iskallia.vault.item.gear.EtchingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(value = EtchingItem.class, remap = false)
public class MixinEtchingItem {
    @Inject(method = "loadModels", at = @At("TAIL"))
    public void loadModels(Consumer<ModelResourceLocation> consumer, CallbackInfo ci, @Local(name = "config") VaultEtchingConfig config) {
        Collection<ResourceLocation> packModels = Minecraft.getInstance().getResourceManager().listResources("models/item/gear/etching", s -> s.endsWith(".json"));
        for(ResourceLocation loc : packModels) {

            if (loc.getNamespace().equals("the_vault")) {
                if(config.getEtchingIds().stream().anyMatch(resourceLocation -> resourceLocation.getPath().replace("etching_", "").equals(ResourceLocUtils.getStrippedPath(ResourceLocUtils.removeSuffixFromId(".json", loc))))) {
                    continue;
                }

                VHAPILoggerUtils.debug("Register custom etching model: " + loc);
                consumer.accept(ResourceLocUtils.stripLocationForItemModel(loc));
            }


        }

        for(Map.Entry<ResourceLocation, VaultEtchingConfig.EtchingEntry> entry : config.ETCHINGS.entrySet()) {
            String var10000 = (entry.getKey()).getPath();
            String itemLocation = VaultMod.sId("gear/etching/" + var10000.replace("etching_", ""));
            consumer.accept(new ModelResourceLocation(itemLocation + "#inventory"));
        }
    }
}
