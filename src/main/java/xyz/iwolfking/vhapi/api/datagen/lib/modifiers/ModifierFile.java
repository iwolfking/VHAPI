package xyz.iwolfking.vhapi.api.datagen.lib.modifiers;

import java.util.Map;

public record ModifierFile(Map<String, Map<String, ModifierDefinition>> modifiers) {}

