package xyz.iwolfking.vaultcrackerlib.api.registry.objective;

import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.system.CallbackI;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomVaultObjectiveRegistry {

    private static final List<CustomVaultObjectiveEntry> CUSTOM_VAULT_OBJECTIVE_ENTRIES = new ArrayList<>();

    public static void addEntry(CustomVaultObjectiveEntry entry) {
        VaultRegistry.OBJECTIVE.add(entry.key());
        CrystalData.OBJECTIVE.register(entry.id(), entry.crystalObjective(), entry.crystalObjectiveSupplier());
    }

    public static int getSize() {
        return CUSTOM_VAULT_OBJECTIVE_ENTRIES.size();
    }

    public static void registerAllCustomVaultObjectives() {

    }


    public static List<CustomVaultObjectiveEntry> getCustomVaultObjectiveEntries() {
        System.out.println("GETTING LIST OF C OBJECTIVES: " + CUSTOM_VAULT_OBJECTIVE_ENTRIES.size());
        return CUSTOM_VAULT_OBJECTIVE_ENTRIES;
    }

    public static Map<String, TextComponent> getBountyScreenMap() {
        Map<String, TextComponent> bountyMap = new HashMap<>();

        for(CustomVaultObjectiveEntry entry : getCustomVaultObjectiveEntries()) {
            bountyMap.put(entry.id(), new TextComponent(entry.name()));
        }

        return bountyMap;

    }

    public static boolean contains(String id) {
        for(CustomVaultObjectiveEntry entry : getCustomVaultObjectiveEntries()) {
            if(id.equals(entry.id())) {
                return true;
            }
        }
        return false;
    }
}
