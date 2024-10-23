package xyz.iwolfking.vhapi.api.events;

import net.minecraftforge.eventbus.api.Event;
import xyz.iwolfking.vhapi.api.loaders.lib.VHAPIDataLoader;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

/**
 * An event fired when the LoaderRegistry initializes and finishes loading all the config loaders.
 */
public class ConfigDataLoaderEvent extends Event {

    public static class Init extends ConfigDataLoaderEvent {
        private final VaultConfigProcessor<?> loader;

        public Init(VaultConfigProcessor<?> loader) {
            this.loader = loader;
        }
        public VaultConfigProcessor<?> getLoader() {
            return loader;
        }
    }

    public static class Finish extends ConfigDataLoaderEvent {
        private final VHAPIDataLoader loader;

        public Finish(VHAPIDataLoader loader) {
            this.loader = loader;
        }

        public VHAPIDataLoader getLoader() {
            return loader;
        }
    }


}
