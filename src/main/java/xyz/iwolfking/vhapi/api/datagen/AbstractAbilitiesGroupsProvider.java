package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.AbilitiesGroups;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.skill.ability.AbilityType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGroupsAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractAbilitiesGroupsProvider extends AbstractVaultConfigDataProvider<AbstractAbilitiesGroupsProvider.Builder> {
    protected AbstractAbilitiesGroupsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/group", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Abilities Groups Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<AbilitiesGroups> {
        Map<AbilityType, List<String>> types = new LinkedHashMap<>();

        public Builder() {
            super(AbilitiesGroups::new);
        }

        public Builder addAbilitiesToGroup(AbilityType type, Consumer<List<String>> abilitiesConsumer) {
            List<String> abilities = new ArrayList<>();
            abilitiesConsumer.accept(abilities);
            types.put(type, abilities);
            return this;
        }

        @Override
        protected void configureConfig(AbilitiesGroups config) {
            ((AbilitiesGroupsAccessor)config).setTypes(types);
        }
    }
}
