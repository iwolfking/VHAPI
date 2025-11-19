package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear;

// helper interface for value types that can produce Json themselves
public interface JsonSerializableValue {
    com.google.gson.JsonElement toJson();
}
