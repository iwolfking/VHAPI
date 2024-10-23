package xyz.iwolfking.vhapi.api.depr.patchers;



import iskallia.vault.util.data.WeightedList;

import java.util.HashSet;
import java.util.Set;
@Deprecated
public class WeightedListInjector<T> extends BasicPatcher {

    private WeightedList<T> WEIGHTED_LIST_ADDITIONS = new WeightedList<>();

    private Set<T> WEIGHTED_LIST_REMOVALS = new HashSet<>();

    public void add(T patch, int weight) {
        WEIGHTED_LIST_ADDITIONS.add(patch, weight);
    }

    public void remove(T name) {
        WEIGHTED_LIST_REMOVALS.add(name);
    }

    public WeightedList<T> getAdditions() {
        return WEIGHTED_LIST_ADDITIONS;
    }

    public Set<T> getRemovals() {
        return WEIGHTED_LIST_REMOVALS;
    }

    public void doPatches(WeightedList<T> listToPatch) {
        if(!this.isPatched()) {
            for(T name : this.getRemovals()) {
                listToPatch.removeEntry(name);
            }
            listToPatch.addAll(WEIGHTED_LIST_ADDITIONS);
            this.setPatched(true);
        }
    }

}
