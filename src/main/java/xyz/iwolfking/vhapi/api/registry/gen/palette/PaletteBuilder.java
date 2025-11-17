package xyz.iwolfking.vhapi.api.registry.gen.palette;

import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
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

    public PaletteDefinition build() { return def; }
}

