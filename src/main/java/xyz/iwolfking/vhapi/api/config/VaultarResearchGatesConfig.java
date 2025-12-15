package xyz.iwolfking.vhapi.api.config;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;

import java.util.HashMap;
import java.util.Map;

public class VaultarResearchGatesConfig extends Config {
    @Expose
    public Map<String, String> entries = new HashMap<>();

    @Override
    public String getName() {
        return "vault_altar/research_gates";
    }

    @Override
    protected void reset() {
        entries.put("powah", "Powah");
        entries.put("refinedstorage", "Refined Storage");
        entries.put("ae2", "Applied Energistics");
        entries.put("botania", "Botania");
        entries.put("integrateddynamics", "Integrated Dynamics");
        entries.put("mekanism", "Mekanism");
        entries.put("moremekanismprocessing", "Mekanism");
        entries.put("immersivenengineering", "Immersive Engineering");
        entries.put("thermal", "Thermal Expansion");
        entries.put("create", "Create");
        entries.put("occultism", "Occultism");
        entries.put("ars_nouveau", "Ars Nouveau");
        entries.put("createaddition", "Create Crafts and Additions");
        entries.put("industrialforegoing", "Industrial Foregoing");
        entries.put("hostilenetworks", "Hostile Neural Networks");
        entries.put("mysticalagriculture", "Mystical Agriculture");
        entries.put("fluxnetworks", "Flux Networks");
        entries.put("botanicalmachinery", "Botanical Machinery");
    }
}
