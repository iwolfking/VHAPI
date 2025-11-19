package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.modifiers;

import java.util.Map;

public record ModifierDefinition(
        Map<String, Object> properties,
        ModifierDisplay display
) {}
