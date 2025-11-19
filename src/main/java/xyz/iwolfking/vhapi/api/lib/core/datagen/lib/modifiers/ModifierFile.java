package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.modifiers;

import java.util.Map;

public record ModifierFile(Map<String, Map<String, ModifierDefinition>> modifiers) {}

