package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette;

import com.google.gson.JsonArray;
import iskallia.vault.block.PlaceholderBlock;
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

    public PaletteBuilder weightedTargetGeneric(String target, Consumer<JsonArray> consumer) {
        def.addTileProcessor(new PaletteProcessors.WeightedGenericProcessor(target, consumer));
        return this;
    }

    public PaletteBuilder weightedTargetGeneric(ResourceLocation target, Consumer<JsonArray> consumer) {
        return weightedTargetGeneric(target.toString(), consumer);
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

    public PaletteBuilder placeholder(PlaceholderBlock.Type target, Consumer<PlaceholderBuilder> consumer) {
        PlaceholderBuilder lb = new PlaceholderBuilder(target);
        consumer.accept(lb);
        def.addTileProcessor(lb.build());
        return this;
    }

    public static class LeveledBuilder {
        private final PaletteProcessors.LeveledProcessor processor =
                new PaletteProcessors.LeveledProcessor();

        public LeveledBuilder weighted(int level, String type, String target, int weight, Consumer<Map<String , Integer>> entries) {
            processor.addLevel(new PaletteProcessors.WeightedLevelProcessor(level, type, target, weight, entries));
            return this;
        }

        public LeveledBuilder list(int level, String type, String target, Consumer<Map<String , Integer>> entries) {
            processor.addLevel(new PaletteProcessors.WeightedTargetLevelProcessor(level, target, entries));
            return this;
        }

        public LeveledBuilder probability(int level, double probability, String target, Consumer<Map<String , Integer>> successes, Consumer<Map<String , Integer>> failures) {
            processor.addLevel(new PaletteProcessors.ProbabilityLevelProcessor(level, probability, successes, failures));
            return this;
        }

        PaletteProcessors.LeveledProcessor build() {
            return processor;
        }
    }

    public static class PlaceholderBuilder {
        private final PaletteProcessors.PlaceholderProcessor processor;

        public PlaceholderBuilder(PlaceholderBlock.Type target) {
            this.processor = new PaletteProcessors.PlaceholderProcessor(target);
        }

        public PlaceholderBuilder weighted(int level, String type, String target, int weight, Consumer<Map<String , Integer>> entries) {
            processor.addLevel(new PaletteProcessors.WeightedLevelProcessor(level, type, target, weight, entries));
            return this;
        }

        public PlaceholderBuilder list(int level, String target, Consumer<Map<String , Integer>> entries) {
            processor.addLevel(new PaletteProcessors.WeightedTargetLevelProcessor(level, target, entries));
            return this;
        }

        public PlaceholderBuilder probability(int level, double probability, Consumer<Map<String , Integer>> successes, Consumer<Map<String , Integer>> failures) {
            processor.addLevel(new PaletteProcessors.ProbabilityLevelProcessor(level, probability, successes, failures));
            return this;
        }

        PaletteProcessors.PlaceholderProcessor build() {
            return processor;
        }
    }


    public PaletteDefinition build() { return def; }
}

