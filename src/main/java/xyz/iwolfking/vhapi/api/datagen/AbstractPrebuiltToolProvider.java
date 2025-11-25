package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonObject;
import iskallia.vault.config.PreBuiltToolConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.ToolMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.PrebuiltToolConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractPrebuiltToolProvider extends AbstractVaultConfigDataProvider<AbstractPrebuiltToolProvider.Builder> {
    protected AbstractPrebuiltToolProvider(DataGenerator generator, String modid) {
        super(generator, modid, "prebuilt_tools", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Prebuilt Tools Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<PreBuiltToolConfig> {
        private final Map<String, Map<String, Map<String, Object>>> tools = new HashMap<>();

        public Builder() {
            super(PreBuiltToolConfig::new);
        }

        public Builder addTool(String toolId, Consumer<ToolAffixBuilder> builderConsumer) {
            ToolAffixBuilder builder = new ToolAffixBuilder();
            builderConsumer.accept(builder);
            tools.put(toolId, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(PreBuiltToolConfig config) {
            ((PrebuiltToolConfigAccessor)config).setTools(tools);
        }

        public static class ToolAffixBuilder {
            private final Map<String, Map<String, Object>> toolAffixes = new HashMap<>();
            private final Map<String, Object> implicits = new HashMap<>();
            private final Map<String, Object> prefixes = new HashMap<>();

            public void material(ToolMaterial toolMaterial) {
                toolAffixes.put("tool_material", Map.of("value", toolMaterial.name()));
            }

            public <T> void implicit(VaultGearAttribute<T> attribute, T value) {
                implicits.put(attribute.getRegistryName().toString(), value);
            }

            public void implicit(VaultGearAttribute<Float> attribute, FloatRoll value) {
                implicits.put(attribute.getRegistryName().toString(), value);
            }

            public void implicit(VaultGearAttribute<Integer> attribute, IntRoll value) {
                implicits.put(attribute.getRegistryName().toString(), value);
            }

            public <T> void prefix(VaultGearAttribute<T> attribute, T value) {
                prefixes.put(attribute.getRegistryName().toString(), value);
            }

            public void prefix(VaultGearAttribute<Float> attribute, FloatRoll value) {
                prefixes.put(attribute.getRegistryName().toString(), value);
            }

            public void prefix(VaultGearAttribute<Integer> attribute, IntRoll value) {
                prefixes.put(attribute.getRegistryName().toString(), value);
            }

            public Map<String, Map<String, Object>> build() {
                toolAffixes.put("IMPLICIT", implicits);
                toolAffixes.put("PREFIX", prefixes);
                return toolAffixes;
            }
        }
    }
}
