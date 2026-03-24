package xyz.iwolfking.vhapi.api.datagen.lib.modifier_pools;

import java.util.List;

public record PoolEntry(int min, int max, List<PoolWeight> pool) {}
