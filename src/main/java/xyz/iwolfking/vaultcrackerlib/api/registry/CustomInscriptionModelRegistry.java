package xyz.iwolfking.vaultcrackerlib.api.registry;

import java.util.HashMap;
import java.util.Map;

public class CustomInscriptionModelRegistry {
    private static final Map<Integer, String> CUSTOM_INSCRIPTION_MODELS = new HashMap<>();

    public static void addModel(int id, String modelName) {
        CUSTOM_INSCRIPTION_MODELS.put(id, modelName);
    }

    public static int getSize() {
        return CUSTOM_INSCRIPTION_MODELS.size();
    }

    public static void registerModels() {
    }

    public static Map<Integer, String> getModelMap() {
        return CUSTOM_INSCRIPTION_MODELS;
    }
}
