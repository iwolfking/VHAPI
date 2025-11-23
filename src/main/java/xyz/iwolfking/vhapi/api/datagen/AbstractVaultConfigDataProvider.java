package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import iskallia.vault.config.Config;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVaultConfigDataProvider implements DataProvider {
    private final DataGenerator generator;
    public final String modid;
    private final Gson gson = Config.GSON;
    protected final Map<String, Config> configMap = new HashMap<>();
    protected final Map<String, Map.Entry<String, Config>> extendedConfigMap = new HashMap<>();
    private final String configPath;

    protected AbstractVaultConfigDataProvider(DataGenerator generator, String modid, String configPath) {
        this.generator = generator;
        this.modid = modid;
        this.configPath = configPath;
    }

    public void addConfig(String fileName, Config config) {
        configMap.put(fileName, config);
    }

    public void addConfig(String fileName, String path, Config config) {
        extendedConfigMap.put(fileName, Map.entry(path, config));
    }

    public abstract void registerConfigs();

    @Override
    public void run(HashCache cache) throws IOException {
        configMap.clear();
        extendedConfigMap.clear();
        registerConfigs();

        configMap.forEach(((s, t) -> {
            Path out = generator.getOutputFolder()
                    .resolve("data/" + modid + "/vault_configs/" + configPath + "/" + s + ".json");

            try {
                DataProvider.save(gson, cache, gson.toJsonTree(t), out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        extendedConfigMap.forEach((name, entry) -> {
            String path = entry.getKey();
            Config config = entry.getValue();

            Path out = generator.getOutputFolder()
                    .resolve("data/" + modid + "/vault_configs/" + path + "/" + name + ".json");

            try {
                DataProvider.save(gson, cache, gson.toJsonTree(config), out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

}
