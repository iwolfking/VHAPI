package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapInjector<S, T> extends BasicPatcher {

    private Map<S,T> CONFIG_PATCHES_MAP= new HashMap<>();

    private Set<S> REMOVAL_SET = new HashSet<>();

    public void put(S name, T patch) {
        CONFIG_PATCHES_MAP.put(name, patch);
    }

    public void remove(S name) {
        REMOVAL_SET.add(name);
    }

    public Map<S, T> getAdditions() {
        return CONFIG_PATCHES_MAP;
    }

    public Set<S> getRemovals() {
        return REMOVAL_SET;
    }

    public void doPatches(Map<S, T> mapToPatch) {
        if(!this.isPatched()) {
            for(S name : this.getRemovals()) {
                mapToPatch.remove(name);
            }
            mapToPatch.putAll(this.getAdditions());
            this.setPatched(true);
        }
    }

}
