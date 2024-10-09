package xyz.iwolfking.vhapi.api.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.Map;

/**
 * An event fired when the LoaderRegistry initializes and finishes loading all the config loaders.
 */
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
