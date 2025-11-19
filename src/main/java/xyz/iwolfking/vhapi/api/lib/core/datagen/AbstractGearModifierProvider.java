package xyz.iwolfking.vhapi.api.lib.core.datagen;


import com.google.gson.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear.ModifierGroupBuilder;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear.ModifierGroupFile;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette.PaletteBuilder;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette.PaletteDefinition;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractGearModifierProvider implements DataProvider {


    protected final DataGenerator generator;
    protected final String modid;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<String, Consumer<ModifierGroupBuilder>> files = new HashMap<>();

    public AbstractGearModifierProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }
    /** Subclass overrides this and calls builder("name") to fill entries */
    public abstract void addModifierGroups(Map<String, Consumer<ModifierGroupBuilder>> map);

    @Override
    public void run(HashCache output) throws IOException {
        addModifierGroups(files);

        for (var entry : files.entrySet()) {
            String fileName = entry.getKey();
            ModifierGroupBuilder builder = new ModifierGroupBuilder();
            entry.getValue().accept(builder);

            ModifierGroupFile file = builder.build();
            JsonElement json = file.toJson();

            Path path = generator.getOutputFolder().resolve(
                    "data/" + modid + "/vault_configs/gear/gear_modifiers/" + fileName + ".json"
            );

            DataProvider.save(GSON, output, json, path);
        }
    }

    @Override
    public String getName() {
        return modid + " ModifierGroupProvider";
    }

}
