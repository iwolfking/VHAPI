package xyz.iwolfking.vhapi.api.registry.gen.palette;

import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Consumer;

public final class PaletteBuilder {
    private final PaletteDefinition def = new PaletteDefinition();

    public PaletteBuilder reference(String id) {
        def.addTileProcessor(new PaletteProcessors.ReferenceProcessor(id));
        return this;
    }

    public PaletteBuilder reference(ResourceLocation id) {
        return reference(id.toString());
    }

    public PaletteBuilder weightedTarget(String target, Consumer<WeightedBuilder> consumer) {
        WeightedBuilder wb = new WeightedBuilder(target);
        consumer.accept(wb);
        def.addTileProcessor(wb.build());
        return this;
    }

    public PaletteBuilder weightedTarget(ResourceLocation target, Consumer<WeightedBuilder> consumer) {
        return weightedTarget(target.toString(), consumer);
    }



    public PaletteBuilder templateStackTile(String target, String... stack) {
        def.addTileProcessor(new PaletteProcessors.TemplateStackTileProcessor(target, Arrays.asList(stack)));
        return this;
    }

    public PaletteBuilder templateStackTile(ResourceLocation target, String... stack) {
        return templateStackTile(target.toString(), stack);
    }

    public PaletteBuilder templateStackSpawner(String target, String... stack) {
        def.addTileProcessor(new PaletteProcessors.TemplateStackSpawnerProcessor(target, Arrays.asList(stack)));
        return this;
    }

    public PaletteBuilder templateStackSpawner(ResourceLocation target, String... stack) {
        return templateStackSpawner(target.toString());
    }

    public PaletteBuilder leveled(Consumer<LeveledBuilder> consumer) {
        LeveledBuilder lb = new LeveledBuilder();
        consumer.accept(lb);
        def.addTileProcessor(lb.build());
        return this;
    }

    public static class LeveledBuilder {
        private final PaletteProcessors.LeveledProcessor processor =
                new PaletteProcessors.LeveledProcessor();

        public LeveledBuilder weighted(int level, String type, String target, int weight, Consumer<Map<String , Integer>> entries) {
            Map<String, Integer> entryMap = new HashMap<>();
            entries.accept(entryMap);
            processor.addLevel(new PaletteProcessors.LevelProcessor().weightedLevel(level, target, weight, entryMap));
            return this;
        }

        public LeveledBuilder list(int level, String type, String target, Consumer<Map<String , Integer>> entries) {
            Map<String, Integer> entryMap = new HashMap<>();
            entries.accept(entryMap);
            processor.addLevel(new PaletteProcessors.LevelProcessor().level(level, target, entryMap));
            return this;
        }

        PaletteProcessors.LeveledProcessor build() {
            return processor;
        }
    }

    public PaletteDefinition build() { return def; }
}

