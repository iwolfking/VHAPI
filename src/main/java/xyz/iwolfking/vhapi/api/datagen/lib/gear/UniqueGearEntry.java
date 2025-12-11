package xyz.iwolfking.vhapi.api.datagen.lib.gear;

import com.google.gson.JsonObject;
import iskallia.vault.config.UniqueCodexConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public record UniqueGearEntry(
        String name,
        List<WeightedModel> models,
        Map<String, List<String>> modifierIdentifiers,
        List<String> modifierTags,
        int weight,
        String modelType,
        UniqueCodexConfig.IntroductionPage.SlotType codexSlotType,
        String codexDropLocation,
        List<JsonObject> codexDescription
) {
    public record WeightedModel(ResourceLocation value, int weight) {}
}
