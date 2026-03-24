package xyz.iwolfking.vhapi.api.datagen.lib.gear;

import java.util.List;

public record ModifierEntry(String attribute, String group, String identifier, List<String> tags, List<Tier> tiers) {
    // for compatibility with the toJson implementation above you can add getX methods:
    public String getAttribute() { return attribute; }
    public String getGroup() { return group; }
    public String getIdentifier() { return identifier; }
    public List<String> getTags() { return tags; }
    public List<Tier> getTiers() { return tiers; }
}
