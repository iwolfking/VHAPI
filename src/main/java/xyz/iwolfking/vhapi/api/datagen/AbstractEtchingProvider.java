package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.gear.EtchingTierConfig;
import iskallia.vault.config.gear.VaultEtchingConfig;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.IGenericBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.util.gear.GearModifierRegistryHelper;
import xyz.iwolfking.vhapi.mixin.accessors.VaultEtchingConfigEntryAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractEtchingProvider extends AbstractVaultConfigDataProvider<AbstractEtchingProvider.Builder>{
    protected AbstractEtchingProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/etching", AbstractEtchingProvider.Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Etchings Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultEtchingConfig> {
        public Map<ResourceLocation, VaultEtchingConfig.EtchingEntry> ETCHINGS = new LinkedHashMap<>();

        public Builder() {
            super(VaultEtchingConfig::new);
        }

        public AbstractEtchingProvider.Builder addEtching(ResourceLocation etchingId, String name, String description, int color, VaultGearModifier.AffixType affixType, Consumer<EtchingEntryBuilder> entryConsumer) {
            EtchingEntryBuilder builder = new EtchingEntryBuilder(name, description, color, affixType);
            entryConsumer.accept(builder);
            ETCHINGS.put(etchingId, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(VaultEtchingConfig config) {
            config.ETCHINGS.putAll(ETCHINGS);
        }
    }

    public static class EtchingEntryBuilder implements IGenericBuilder<VaultEtchingConfig.EtchingEntry> {

        private final String name;
        private final String description;
        private final int color;
        private int minGreedTier = 0;
        private final List<String> typeGroups = new ArrayList<>();
        EtchingTierConfig.EtchingAttributeGroup attributes = new EtchingTierConfig.EtchingAttributeGroup();
        private VaultGearModifier.AffixType affixType;

        VaultEtchingConfig.EtchingEntry entry;

        public EtchingEntryBuilder(String name, String description, int color, VaultGearModifier.AffixType affixType) {
            this.name = name;
            this.description = description;
            this.color = color;
            this.affixType = affixType;
        }

        public EtchingEntryBuilder minGreedTier(int minGreedTier) {
            this.minGreedTier = minGreedTier;
            return this;
        }

        public EtchingEntryBuilder typeGroups(Consumer<BasicListBuilder<String>> builderConsumer) {
            BasicListBuilder<String> builder = new BasicListBuilder<>();
            builderConsumer.accept(builder);
            typeGroups.addAll(builder.build());
            return this;
        }

        public EtchingEntryBuilder attribute(ResourceLocation modifierTypeId, String modifierGroup, ResourceLocation modifierId, Consumer<BasicListBuilder<EtchingTierConfig.EtchingModifierTier<?>>> tierBasicListBuilderConsumer) {
            EtchingTierConfig.EtchingModifierTierGroup tierGroup = GearModifierRegistryHelper.createEtching(modifierTypeId, modifierGroup, modifierId);
            BasicListBuilder<EtchingTierConfig.EtchingModifierTier<?>> builder = new BasicListBuilder<>();
            tierBasicListBuilderConsumer.accept(builder);
            tierGroup.addAll(builder.build());
            attributes.add(tierGroup);
            return this;
        }

        @Override
        public VaultEtchingConfig.EtchingEntry build() {
            entry = new VaultEtchingConfig.EtchingEntry(name, description, color);
            ((VaultEtchingConfigEntryAccessor)entry).setAffixType(affixType);
            ((VaultEtchingConfigEntryAccessor)entry).setMinGreedTier(minGreedTier);
            ((VaultEtchingConfigEntryAccessor)entry).setTypeGroups(typeGroups);
            ((VaultEtchingConfigEntryAccessor)entry).setEtchingAttributeGroup(attributes);
            return entry;
        }
    }
}
