package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.core.vault.modifier.modifier.GroupedModifier;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.VaultModifiersConfigAccessor;
import xyz.iwolfking.woldsvaults.WoldsVaults;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultModifiersProvider extends AbstractVaultConfigDataProvider{
    protected AbstractVaultModifiersProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/modifiers");
    }

    @Override
    public String getName() {
        return modid + " Vault Modifiers Provider";
    }

    public static class VaultModifiersConfigBuilder {
        VaultModifiersConfig.ModifierTypeGroups modifierTypeGroups;

        public VaultModifiersConfigBuilder() {
             createModifierTypeGroups();
        }

        private void createModifierTypeGroups() {
            Class<?> clazz = VaultModifiersConfig.ModifierTypeGroups.class;

            try {
                Constructor<?> ctor = clazz.getDeclaredConstructor();
                modifierTypeGroups = (VaultModifiersConfig.ModifierTypeGroups) ctor.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public VaultModifiersConfigBuilder addModifiersToGroup(ResourceLocation modifierTypeGroupId, Consumer<Map<ResourceLocation, VaultModifier<?>>> vaultModifiersConsumer) {
            Map<ResourceLocation, VaultModifier<?>> modifierMap = new HashMap<>();
            vaultModifiersConsumer.accept(modifierMap);
            if(modifierTypeGroups.containsKey(modifierTypeGroupId)) {
                modifierMap.forEach(modifierTypeGroups.get(modifierTypeGroupId)::put);
            }
            else {
                modifierTypeGroups.put(modifierTypeGroupId, modifierMap);
            }
            return this;
        }

        public VaultModifiersConfig build() {
            VaultModifiersConfig config = new VaultModifiersConfig();
            ((VaultModifiersConfigAccessor)config).setModifierTypeGroups(modifierTypeGroups);
            return config;
        }
    }
}
