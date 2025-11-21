package xyz.iwolfking.vhapi.api.lib.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractSkillDescriptionsProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modid;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, JsonArray> descriptions = new LinkedHashMap<>();

    public AbstractSkillDescriptionsProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        descriptions.clear();
        registerDescriptions();

        Map<String, Map<String, JsonArray>> output = new HashMap<>();
        output.put("descriptions", descriptions);

        Path out = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/skill/descriptions/descriptions.json");

        DataProvider.save(gson, cache, gson.toJsonTree(output), out);
    }

    public abstract void registerDescriptions();


    public void addDescription(String name, Consumer<JsonArray> descriptionConsumer) {
        JsonArray descriptionsArray = new JsonArray();
        descriptionConsumer.accept(descriptionsArray);
        descriptions.put(name, descriptionsArray);
    }

    @Override
    public String getName() {
        return modid + " skill descriptions provider";
    }
}
