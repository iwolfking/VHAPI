package xyz.iwolfking.vaultcrackerlib.api.registry.models;


import java.util.HashMap;
import java.util.Map;

public class CustomCatalystModelRegistry {
    private static final Map<Integer, String> CUSTOM_CATALYST_MODELS = new HashMap<>();

    public static void addModel(int id, String modelName) {
        CUSTOM_CATALYST_MODELS.put(id, modelName);
    }

    public static int getSize() {
        return CUSTOM_CATALYST_MODELS.size();
    }

    public static Map<Integer, String> getModelMap() {
        return CUSTOM_CATALYST_MODELS;
    }

}
