package xyz.iwolfking.vhapi.api.registry.gen.palette;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public final class PaletteDefinition {
    private final List<PaletteProcessors.TileProcessor> tileProcessors = new ArrayList<>();
    private final List<JsonElement> entityProcessors = new ArrayList<>(); // extend as needed
    private final List<JsonElement> decorators = new ArrayList<>();

    public void addTileProcessor(PaletteProcessors.TileProcessor p) { tileProcessors.add(p); }
    public void addEntityProcessor(JsonElement e) { entityProcessors.add(e); }
    public void addDecorator(JsonElement e) { decorators.add(e); }

    public JsonObject toJson() {
        JsonObject root = new JsonObject();

        JsonArray tiles = new JsonArray();
        for (PaletteProcessors.TileProcessor p : tileProcessors) tiles.add(p.toJson());
        root.add("tile_processors", tiles);

        JsonArray entities = new JsonArray();
        for (JsonElement e : entityProcessors) entities.add(e);
        root.add("entity_processors", entities);

        JsonArray decs = new JsonArray();
        for (JsonElement d : decorators) decs.add(d);
        root.add("decorators", decs);

        return root;
    }
}
