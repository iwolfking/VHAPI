package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import iskallia.vault.config.Config;
import iskallia.vault.config.adapter.WeightedListAdapter;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.adapter.util.IdentifierAdapter;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierFile;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultModifierProvider implements DataProvider {


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter(ResourceLocation .class, new IdentifierAdapter(true)).registerTypeAdapterFactory(WeightedListAdapter.Factory.INSTANCE).registerTypeHierarchyAdapter(TilePredicate.class, Adapters.TILE_PREDICATE).registerTypeHierarchyAdapter(EntityPredicate.class, Adapters.ENTITY_PREDICATE).create();
    private final DataGenerator gen;
    private final String modid;


    public AbstractVaultModifierProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        Map<String, Consumer<ModifierBuilder>> map = new LinkedHashMap<>();
        addFiles(map);

        for (var entry : map.entrySet()) {
            String fileName = entry.getKey();

            ModifierBuilder builder = new ModifierBuilder();
            entry.getValue().accept(builder);
            ModifierFile file = builder.build();

            JsonElement json = GSON.toJsonTree(file);

            Path outPath = gen.getOutputFolder().resolve("data/" + modid + "/vault_configs/vault/modifiers/" + fileName + ".json");

            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }

            DataProvider.save(GSON, cache, json, outPath);
        }
    }

    public abstract void addFiles(Map<String, Consumer<ModifierBuilder>> map);

    @Override
    public String getName() {
        return "VHAPI Modifier Provider";
    }
}

