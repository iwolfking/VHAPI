package xyz.iwolfking.vhapi.api.datagen;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xyz.iwolfking.vhapi.api.datagen.lib.modifier_pools.ModifierPoolBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.modifier_pools.ModifierPoolFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultModifierPoolsProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator gen;
    private final String modid;

    public AbstractVaultModifierPoolsProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }

    @Override
    public void run(HashCache output) throws IOException {
        Map<String, Consumer<ModifierPoolBuilder>> map = new LinkedHashMap<>();
        addFiles(map);

        for (var entry : map.entrySet()) {
            String fileName = entry.getKey();

            ModifierPoolBuilder builder = new ModifierPoolBuilder();
            entry.getValue().accept(builder);

            ModifierPoolFile file = builder.build();

            JsonElement json = GSON.toJsonTree(file);

            Path path = gen.getOutputFolder().resolve(
                    "data/" + modid + "/vault_configs/vault/modifier_pools/" + fileName + ".json"
            );

            DataProvider.save(GSON, output, json, path);
        }
    }

    public abstract void addFiles(Map<String, Consumer<ModifierPoolBuilder>> map);

    @Override
    public String getName() {
        return modid + "Modifier Pool Provider";
    }

}
