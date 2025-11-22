package xyz.iwolfking.vhapi.api.lib.core.datagen;

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
    private final Map<String, Config> configMap = new HashMap<>();
    private final String configPath;

    protected AbstractVaultConfigDataProvider(DataGenerator generator, String modid, String configPath) {
        this.generator = generator;
        this.modid = modid;
        this.configPath = configPath;
    }

    public void addConfig(String fileName, Config config) {
        configMap.put(fileName, config);
    }

    public abstract void registerConfigs();

    @Override
    public void run(HashCache cache) throws IOException {
        configMap.clear();
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


    }

}
