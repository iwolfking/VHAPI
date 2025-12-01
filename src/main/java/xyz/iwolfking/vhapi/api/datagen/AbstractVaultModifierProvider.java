package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultModifierProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
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

