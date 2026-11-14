package xyz.iwolfking.vhapi.api.patches;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.loading.DatagenModLoader;
import xyz.iwolfking.vhapi.VHAPI;

import javax.annotation.Nullable;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public final class ConfigPatches {
    public static final String DIRECTORY = "patches";
    private static final String CONFIG_ROOT = "config/the_vault/";
    private static final String JSON = ".json";
    private static Map<String, List<Entry>> loaded = Collections.emptyMap();
    private static final Map<String, List<Entry>> DATA_RUN = new TreeMap<>();

    private ConfigPatches() {
    }

    public record Entry(String namespace, ConfigPatch patch) {
    }

    public static void reload(Map<ResourceLocation, JsonElement> configs) {
        Map<String, List<Entry>> patches = new TreeMap<>();
        configs.forEach((id, json) -> {
            if (!id.getPath().startsWith(DIRECTORY + "/")) {
                return;
            }
            String path = id.getPath().substring(DIRECTORY.length() + 1) + JSON;
            try {
                ConfigPatch patch = ConfigPatch.fromJson(json.getAsJsonObject());
                patches.computeIfAbsent(path, p -> new ArrayList<>()).add(new Entry(id.getNamespace(), patch));
            } catch (RuntimeException e) {
                VHAPI.LOGGER.error("Config patch {} could not be read; {} loads without it", id, path, e);
            }
        });
        patches.values().forEach(entries -> entries.sort(Comparator.comparing(Entry::namespace)));
        loaded = patches;
    }

    public static void useDataRunPatches(String namespace, Map<String, ConfigPatch> patches) {
        patches.forEach((path, patch) -> {
            List<Entry> entries = DATA_RUN.computeIfAbsent(path, p -> new ArrayList<>());
            entries.removeIf(entry -> entry.namespace().equals(namespace));
            entries.add(new Entry(namespace, patch));
            entries.sort(Comparator.comparing(Entry::namespace));
        });
    }

    @Nullable
    public static String relativePath(File file) {
        return relativePath(file.getPath());
    }

    @Nullable
    public static String relativePath(String path) {
        String normalized = path.replace('\\', '/');
        int at = normalized.indexOf(CONFIG_ROOT);
        return at < 0 ? null : normalized.substring(at + CONFIG_ROOT.length());
    }

    public static List<Entry> find(@Nullable String path) {
        if (path == null) {
            return List.of();
        }
        Map<String, List<Entry>> source = DatagenModLoader.isRunningDataGen() ? DATA_RUN : loaded;
        return source.getOrDefault(path, List.of());
    }

    @Nullable
    public static JsonElement created(String path) {
        List<Entry> entries = find(path);
        Entry creator = null;
        for (Entry entry : entries) {
            if (entry.patch().create() == null) {
                continue;
            }
            if (creator == null) {
                creator = entry;
            } else {
                VHAPI.LOGGER.error("Config patches from {} and {} both create {}; using {}", creator.namespace(), entry.namespace(), path, creator.namespace());
            }
        }
        return creator == null ? null : apply(path, entries, creator.patch().create());
    }

    public static JsonElement apply(String path, List<Entry> entries, JsonElement base) {
        JsonElement json = base;
        for (Entry entry : entries) {
            ConfigPatch patch = entry.patch();
            ConfigPatch.Result result = patch.apply(json);
            for (String failure : result.failures()) {
                VHAPI.LOGGER.error("Config patch {} for {} did not apply {}; the upstream value stays", entry.namespace(), path, failure);
            }
            VHAPI.LOGGER.debug("Applied config patch {} to {} ({} of {} ops)", entry.namespace(), path, patch.ops().size() - result.failures().size(), patch.ops().size());
            json = result.json();
        }
        return json;
    }

    public static Object deserializePatched(String path, List<Entry> entries, Reader reader, Function<Reader, Object> deserialize) {
        JsonElement base = JsonParser.parseReader(reader);
        JsonElement patched = apply(path, entries, base);
        try {
            return deserialize.apply(new StringReader(patched.toString()));
        } catch (RuntimeException e) {
            VHAPI.LOGGER.error("Config patches for {} produced a config the base mod cannot read; loading the unpatched file", path, e);
            return deserialize.apply(new StringReader(base.toString()));
        }
    }
}
