package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultModifiersConfigAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultModifiersProvider extends AbstractVaultConfigDataProvider<AbstractVaultModifiersProvider.Builder> {
    protected AbstractVaultModifiersProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/modifiers", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Vault Modifiers Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultModifiersConfig> {
        VaultModifiersConfig.ModifierTypeGroups modifierTypeGroups;

        public Builder() {
            super(VaultModifiersConfig::new);
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

        public Builder addModifiersToGroup(ResourceLocation modifierTypeGroupId, Consumer<Map<ResourceLocation, VaultModifier<?>>> vaultModifiersConsumer) {
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

        @Override
        protected void configureConfig(VaultModifiersConfig config) {
            ((VaultModifiersConfigAccessor)config).setModifierTypeGroups(modifierTypeGroups);
        }
    }
}
