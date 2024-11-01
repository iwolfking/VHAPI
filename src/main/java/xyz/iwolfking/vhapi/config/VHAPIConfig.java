package xyz.iwolfking.vhapi.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VHAPIConfig {
    public static class Server {
        public final ForgeConfigSpec.ConfigValue<Boolean> syncDatapackConfigs;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("Main Settings");
            this.syncDatapackConfigs = builder.comment("Whether the server should attempt to sync configs with clients with VHAPI installed. (default: true)").define("syncDatapackConfigs", true);
            builder.pop();
        }
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disabledConfigs;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Main Settings");
            this.disabledConfigs = builder.comment("List of resource locations that should not be loaded via VHAPI.").defineList("disabledConfigs", List.of(), entry -> ResourceLocation.isValidResourceLocation((String) entry));
            builder.pop();
        }

        public List<ResourceLocation> getIgnoredConfigs() {
            List<ResourceLocation> locations = new ArrayList<>();
            for(String config : this.disabledConfigs.get()) {
                locations.add(new ResourceLocation(config));
            }

            return locations;
        }
    }

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverSpecPair.getLeft();
        SERVER_SPEC = serverSpecPair.getRight();

        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}
