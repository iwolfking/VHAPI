package xyz.iwolfking.vhapi.api.loaders.general;

import iskallia.vault.config.VaultStashConfig;
import iskallia.vault.init.ModConfigs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.MixinConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultStashConfigAccessor;

public class VaultStashConfigLoader extends VaultConfigProcessor<VaultStashConfig> {
    public VaultStashConfigLoader() {
        super(new VaultStashConfig(), "vault_stash");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultStashConfigAccessor base = (VaultStashConfigAccessor) ModConfigs.VAULT_STASH;
        boolean modified = false;

        for (VaultStashConfig config : this.CUSTOM_CONFIGS.values()) {
            VaultStashConfigAccessor custom = (VaultStashConfigAccessor) config;

            if (custom.getFacetedFocus() != null) {
                base.setFacetedFocus(custom.getFacetedFocus());
                modified = true;
            }
            if (custom.getLogLines() > 0) {
                base.setLogLines(custom.getLogLines());
                modified = true;
            }
            if (custom.getLogMergeSeconds() > 0) {
                base.setLogMergeSeconds(custom.getLogMergeSeconds());
                modified = true;
            }

            if (custom.getCategories() != null && !custom.getCategories().isEmpty()) {
                List<VaultStashConfig.Category> baseCategories = ensureList(base.getCategories(), base::setCategories);
                Map<String, VaultStashConfig.Category> baseById = new HashMap<>();
                for (VaultStashConfig.Category existing : baseCategories) {
                    if (existing != null && existing.id != null) {
                        baseById.put(existing.id, existing);
                    }
                }

                for (VaultStashConfig.Category customCat : custom.getCategories()) {
                    if (customCat == null || customCat.id == null) {
                        continue;
                    }
                    VaultStashConfig.Category target = baseById.get(customCat.id);
                    if (target == null) {
                        target = new VaultStashConfig.Category();
                        target.id = customCat.id;
                        target.name = customCat.name;
                        target.icon = customCat.icon;
                        if (customCat.items != null) {
                            target.items.addAll(customCat.items);
                        }
                        if (customCat.itemTags != null) {
                            target.itemTags.addAll(customCat.itemTags);
                        }
                        baseCategories.add(target);
                        baseById.put(target.id, target);
                    } else {
                        if (customCat.name != null && !customCat.name.isBlank()) {
                            target.name = customCat.name;
                        }
                        if (customCat.icon != null) {
                            target.icon = customCat.icon;
                        }
                        if (customCat.items != null) {
                            for (Item item : customCat.items) {
                                if (item != null && !target.items.contains(item)) {
                                    target.items.add(item);
                                }
                            }
                        }
                        if (customCat.itemTags != null) {
                            for (ResourceLocation tag : customCat.itemTags) {
                                if (tag != null && !target.itemTags.contains(tag)) {
                                    target.itemTags.add(tag);
                                }
                            }
                        }
                    }
                }
                modified = true;
            }

            if (custom.getLadders() != null && !custom.getLadders().isEmpty()) {
                List<VaultStashConfig.Ladder> baseLadders = ensureList(base.getLadders(), base::setLadders);
                baseLadders.addAll(custom.getLadders());
                modified = true;
            }

            if (custom.getBreakdowns() != null && !custom.getBreakdowns().isEmpty()) {
                List<VaultStashConfig.Breakdown> baseBreakdowns = ensureList(base.getBreakdowns(), base::setBreakdowns);
                Map<Object, VaultStashConfig.Breakdown> byItem = new HashMap<>();
                for (VaultStashConfig.Breakdown breakdown : custom.getBreakdowns()) {
                    if (breakdown != null && breakdown.item != null) {
                        byItem.put(breakdown.item, breakdown);
                    }
                }
                baseBreakdowns.removeIf(existing -> existing != null && existing.item != null && byItem.containsKey(existing.item));
                baseBreakdowns.addAll(custom.getBreakdowns());
                modified = true;
            }

            if (custom.getLooks() != null) {
                VaultStashConfig.Looks baseLooks = base.getLooks();
                if (baseLooks == null) {
                    baseLooks = new VaultStashConfig.Looks();
                    base.setLooks(baseLooks);
                }
                mergeLooksById(baseLooks.models, custom.getLooks().models);
                mergeLooksById(baseLooks.particles, custom.getLooks().particles);
                modified = true;
            }
        }

        if (modified) {
            ((MixinConfigAccessor) ModConfigs.VAULT_STASH).invokeOnLoad(null);
        }

        super.afterConfigsLoad(event);
    }

    private static <T> List<T> ensureList(
            @Nullable List<T> current,
            java.util.function.Consumer<List<T>> setter
    ) {
        if (current == null) {
            current = new ArrayList<>();
            setter.accept(current);
        }
        return current;
    }

    private static void mergeLooksById(
            List<VaultStashConfig.Look> target,
            @Nullable List<VaultStashConfig.Look> source
    ) {
        if (source == null || source.isEmpty()) {
            return;
        }
        Map<String, VaultStashConfig.Look> byId = new HashMap<>();
        for (VaultStashConfig.Look look : source) {
            if (look != null && look.id != null) {
                byId.put(look.id, look);
            }
        }
        target.removeIf(existing -> existing != null && existing.id != null && byId.containsKey(existing.id));
        target.addAll(source);
    }
}