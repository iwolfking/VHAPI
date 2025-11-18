package xyz.iwolfking.vhapi.api.registry.gen.palette;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WeightedBuilder {
    private final String target;
    private final Map<String,Integer> outputs = new LinkedHashMap<>();

    public WeightedBuilder(String target) { this.target = target; }

    public WeightedBuilder add(String result, int weight) {
        outputs.put(result, weight);
        return this;
    }

    public WeightedBuilder add(ResourceLocation result, int weight) {
        return add(result.toString(), weight);
    }

    public PaletteProcessors.WeightedTargetProcessor build() {
        return new PaletteProcessors.WeightedTargetProcessor(target, outputs);
    }
}
