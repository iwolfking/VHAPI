package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib;

import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetInjector<T> extends BasicPatcher {

    private Set<T> CONFIG_PATCHES_SET = new HashSet<>();

    private Set<T> REMOVAL_SET = new HashSet<>();

    public void add(T patch) {
        CONFIG_PATCHES_SET.add(patch);
    }

    public void remove(T name) {
        REMOVAL_SET.add(name);
    }

    public Set<T> getAdditions() {
        return CONFIG_PATCHES_SET;
    }

    public Set<T> getRemovals() {
        return REMOVAL_SET;
    }

    public void doPatches(Collection<T> setToPatch) {
        if(!this.isPatched()) {
            for(T name : this.getRemovals()) {
                setToPatch.remove(name);
            }
            setToPatch.addAll(this.getAdditions());
            this.setPatched(true);
        }
    }

}
