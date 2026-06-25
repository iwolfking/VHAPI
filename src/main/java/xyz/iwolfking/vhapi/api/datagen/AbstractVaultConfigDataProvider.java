package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import iskallia.vault.config.Config;
import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.recipes.AbstractCatalystRecipesProvider;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractVaultConfigDataProvider<B extends VaultConfigBuilder<?>> implements DataProvider {
    private final DataGenerator generator;
    public final String modid;
    private final Gson gson = ConfigData.CONFIG_LOADER_GSON;
    protected final Map<String, Config> configMap = new LinkedHashMap<>();
    protected final Map<String, Map.Entry<String, Config>> extendedConfigMap = new LinkedHashMap<>();
    private final String configPath;
    private final Supplier<B> builderFactory;

    protected AbstractVaultConfigDataProvider(DataGenerator generator, String modid, String configPath, Supplier<B> builderFactory) {
        this.generator = generator;
        this.modid = modid;
        this.configPath = configPath;
        this.builderFactory = builderFactory;
    }

    public void addConfig(String fileName, Config config) {
        configMap.put(fileName, config);
    }


    protected <C extends Config> void add(String fileName, Consumer<B> consumer) {
        B builder = this.builderFactory.get();
        consumer.accept(builder);

        C newConfig = (C) builder.build();

        if (this.configMap.containsKey(fileName)) {
            Config existingConfig = this.configMap.get(fileName);

            if (builder instanceof MergableBuilder) {
                MergableBuilder rawBuilder = (MergableBuilder) builder;

                rawBuilder.mergeInto(existingConfig, newConfig);
            } else {
                this.configMap.put(fileName, newConfig);
            }
        } else {
            this.configMap.put(fileName, newConfig);
        }
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
    }


    public interface Buildable<C extends Config> {
        C build();
    }

    public interface MergableBuilder<C extends Config> {
        void mergeInto(C existingConfig, C newConfig);
    }

}
