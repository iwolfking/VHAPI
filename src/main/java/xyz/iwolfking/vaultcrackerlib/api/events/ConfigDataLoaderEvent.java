package xyz.iwolfking.vaultcrackerlib.api.events;

import iskallia.vault.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.Map;
import java.util.Set;

public class ConfigDataLoaderEvent extends Event {

    public static class Init extends ConfigDataLoaderEvent {
        private VaultConfigDataLoader<?> loader;

        public Init(VaultConfigDataLoader<?> loader) {
            this.loader = loader;
        }
        public VaultConfigDataLoader<?> getLoader() {
            return loader;
        }
    }

    public static class Finish extends ConfigDataLoaderEvent {
        private Map<ResourceLocation, VaultConfigDataLoader<?>> loaders;

        public Finish(Map<ResourceLocation, VaultConfigDataLoader<?>> loaders) {
        this.loaders = loaders;
        }

        public Map<ResourceLocation, VaultConfigDataLoader<?>>getLoaders() {
            return loaders;
        }
    }


}
