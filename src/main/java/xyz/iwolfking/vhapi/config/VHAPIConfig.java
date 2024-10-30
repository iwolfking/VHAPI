package xyz.iwolfking.vhapi.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class VHAPIConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> syncDatapackConfigs;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Main Settings");
            this.syncDatapackConfigs = builder.comment("Whether the server should attempt to sync configs with clients with VHAPI installed. (default: true)").define("syncDatapackConfigs", true);
            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = serverSpecPair.getLeft();
        COMMON_SPEC = serverSpecPair.getRight();
    }
}
