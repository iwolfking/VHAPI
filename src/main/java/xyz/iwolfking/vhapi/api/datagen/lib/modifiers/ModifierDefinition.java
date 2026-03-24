package xyz.iwolfking.vhapi.api.datagen.lib.modifiers;

import java.util.Map;

public record ModifierDefinition(
        Map<String, Object> properties,
        ModifierDisplay display
) {}
