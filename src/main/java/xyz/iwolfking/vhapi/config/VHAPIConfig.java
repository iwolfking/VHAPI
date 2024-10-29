package xyz.iwolfking.vhapi.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class VHAPIConfig {
    public static class Server {
        public final ForgeConfigSpec.ConfigValue<Boolean> syncDatapackConfigs;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("Main Settings");
            this.syncDatapackConfigs = builder.comment("Whether the server should attempt to sync configs with clients with VHAPI installed. (default: true)").define("syncDatapackConfigs", true);
            builder.pop();
        }
    }

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverSpecPair.getLeft();
        SERVER_SPEC = serverSpecPair.getRight();
    }
}
