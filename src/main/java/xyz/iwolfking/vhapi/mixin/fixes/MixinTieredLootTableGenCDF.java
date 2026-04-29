package xyz.iwolfking.vhapi.mixin.fixes;

import iskallia.vault.core.world.loot.generator.TieredLootTableGenerator;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static iskallia.vault.core.world.loot.generator.TieredLootTableGenerator.CDF.FACTORIAL;

@Mixin(value = TieredLootTableGenerator.CDF.class, remap = false)
public abstract class MixinTieredLootTableGenCDF {
    @Shadow @Final private int samples;

    @Shadow public abstract void permute(int sum, int total, int depth, int[] stack, Consumer<int[]> action);

    @Shadow public abstract double getHeuristic(int[] frequencies);

    @Shadow public abstract long pack(int[] frequencies);

    @Shadow @Final private double[] weights;


    @Shadow public abstract int[] unpack(long packed);

    @Shadow @Final private double[] probabilities;
    @Unique private static final Map<List<Integer>, Double> vaultCrackerLib$probabilityCache = new Object2ObjectOpenHashMap<>();

    /**
     * @author radimous
     * @reason replace arraymap with hashmap
     */
    @Overwrite
    public Long2DoubleMap compute() {
        Double2ObjectOpenHashMap<LongList> result = new Double2ObjectOpenHashMap<>();
        this.permute(
            0,
            this.samples,
            0,
            new int[this.weights.length],
            stack -> result.computeIfAbsent(this.getHeuristic(stack), l -> new LongArrayList()).add(this.pack(stack))
        );
        List<Double2ObjectMap.Entry<LongList>> sorted = result.double2ObjectEntrySet()
            .stream()
            .sorted(Comparator.comparingDouble(Double2ObjectMap.Entry::getDoubleKey))
            .toList();
        double[] cumulative = new double[]{0.0};
        Long2DoubleMap map = new Long2DoubleOpenHashMap(sorted.size());
        sorted.forEach(e -> {
            for (int j = 0; j < e.getValue().size(); j++) {
                long i = e.getValue().getLong(j);
                cumulative[0] += this.getProbability(this.unpack(i));
                map.put(i, cumulative[0]);
            }
        });
        return map;
    }


    /**
     * @author radimous
     * @reason cache probability
     */
    @Overwrite
    public double getProbability(int[] frequencies) {
        var canonical = IntStream.of(frequencies).sorted().boxed().toList();
        double aDouble = vaultCrackerLib$probabilityCache.computeIfAbsent(canonical, canonicalList -> {
            int sum = 0;

            for(int frequency : frequencies) {
                sum += frequency;
            }

            BigDecimal a = BigDecimal.ONE;

            for(int frequency : frequencies) {
                a = a.multiply(FACTORIAL[frequency]);
            }

            a = FACTORIAL[sum].divide(a, 10, RoundingMode.HALF_UP);

            return a.doubleValue();
        });


        double b = 1.0F;
        for(int i = 0; i < frequencies.length; ++i) {
            b *= Math.pow(this.probabilities[i], frequencies[i]);
        }

        return aDouble * b;
    }
}
