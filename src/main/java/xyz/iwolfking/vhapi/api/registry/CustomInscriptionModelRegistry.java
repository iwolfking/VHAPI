package xyz.iwolfking.vhapi.api.registry;

import java.util.HashMap;
import java.util.Map;

public class CustomInscriptionModelRegistry {

    private static final Map<Integer, String> CUSTOM_INSCRIPTION_MODELS = new HashMap<>();
    /**
     *
     * @param id An integer that will be used to reference this model, do not overlap with any existing ids.
     * @param modelName The name of the model, for reference purposes.
     */
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
