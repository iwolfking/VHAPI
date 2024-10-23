package xyz.iwolfking.vhapi.api.data.api;

import net.minecraft.network.chat.TextComponent;

import java.util.Map;

public class BountyScreenData {
    public static Map<String, TextComponent> OBJECTIVE_NAME = Map.ofEntries(
            Map.entry("obelisk", new TextComponent("Obelisks")),
            Map.entry("boss", new TextComponent("Hunt The Guardians")),
            Map.entry("cake", new TextComponent("Find The Cakes")),
            Map.entry("scavenger", new TextComponent("Scavenger Hunt")),
            Map.entry("vault", new TextComponent("Any Vault")),
            Map.entry("monolith", new TextComponent("Light the Braziers")),
            Map.entry("elixir", new TextComponent("Gather Elixir")),
            Map.entry("bingo", new TextComponent("Bingo"))

    );
}
