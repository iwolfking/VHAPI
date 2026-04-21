package xyz.iwolfking.vhapi.mixin.tags;

import iskallia.vault.VaultMod;
import iskallia.vault.config.greed.GreedCauldronConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.DemandEntryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedCauldronConfigAccessor;
import java.util.*;

@Mixin(TagLoader.class)
public class MixinTagLoader {
    //Credits to rizek/radimous for dynamic tag generation
    @Shadow
    @Final
    private String directory;
    @Inject(method = "build", at = @At("HEAD"))
    private void afterBuild(Map<ResourceLocation, Tag.Builder> pBuilders, CallbackInfoReturnable<Map<ResourceLocation, Tag<?>>> cir) {
        if ("tags/items".equals(this.directory)){
            Set<ResourceLocation> greedCauldronItems = vhapi$getGreedCauldronItems(ModConfigs.GREED_CAULDRON);
            vhapi$createTag(pBuilders,"greed_cauldron_demands", greedCauldronItems);
        }
    }

    @Unique
    private static Set<ResourceLocation> vhapi$getGreedCauldronItems(GreedCauldronConfig config) {
        Set<ResourceLocation> cauldronItems = new HashSet<>();
        ((GreedCauldronConfigAccessor)config).getDemands().forEach(demandEntry -> {
            if(ResourceLocUtils.isResourceLocation(((DemandEntryAccessor)demandEntry).getItem())) {
                ResourceLocation id = ResourceLocation.tryParse(((DemandEntryAccessor)demandEntry).getItem());
                cauldronItems.add(id);
            }
        });
        return cauldronItems;
    }

    @Unique
    private static void vhapi$createTag(Map<ResourceLocation,Tag.Builder> pBuilders,String tag, Set<ResourceLocation> items) {
        Tag.Builder lootTag = pBuilders.computeIfAbsent(VaultMod.id(tag),id -> Tag.Builder.tag());
        items.forEach( item -> {
            if (Registry.ITEM.getOptional(item).isPresent()) {
                lootTag.addElement(item, "Vault Filters dynamic tags");
            }
        });
    }



}