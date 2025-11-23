package xyz.iwolfking.vhapi.api.datagen.lib.gear;

import com.google.gson.JsonObject;
import iskallia.vault.config.UniqueCodexConfig;

import java.util.List;
import java.util.Map;

public record UniqueGearEntry(
        String name,
        String model,
        Map<String, List<String>> modifierIdentifiers,
        List<String> modifierTags,
        int weight,
        String modelType,
        UniqueCodexConfig.IntroductionPage.SlotType codexSlotType,
        String codexDropLocation,
        List<JsonObject> codexDescription
) {}

