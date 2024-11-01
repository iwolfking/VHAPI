package xyz.iwolfking.vhapi.api.events;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import xyz.iwolfking.vhapi.api.loaders.lib.VHAPIDataLoader;

import java.util.Map;

public class VHAPIProcessorsEvent extends Event {
    public static class Init extends VHAPIProcessorsEvent {

        public Init() {
        }

    }

    public static class End extends VHAPIProcessorsEvent {

        public End() {
        }

    }
}
