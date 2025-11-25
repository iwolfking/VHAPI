package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonObject;
import iskallia.vault.config.PreBuiltToolConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.core.data.serializable.ISerializable;
import iskallia.vault.core.world.roll.DoubleRoll;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.ToolMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
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

            public ToolAffixBuilder material(ToolMaterial toolMaterial) {
                toolAffixes.put("tool_material", Map.of("value", toolMaterial.name()));
                return this;
            }

            public <T> ToolAffixBuilder implicit(VaultGearAttribute<T> attribute, T value) {
                implicits.put(attribute.getRegistryName().toString(), value);
                return this;
            }

            public ToolAffixBuilder implicit(VaultGearAttribute<Float> attribute, FloatRoll value) {
                implicits.put(attribute.getRegistryName().toString(), wrapRollWithType(value));
                return this;
            }

            public ToolAffixBuilder implicit(VaultGearAttribute<Integer> attribute, IntRoll value) {
                implicits.put(attribute.getRegistryName().toString(), wrapRollWithType(value));
                return this;
            }

            public <T> ToolAffixBuilder prefix(VaultGearAttribute<T> attribute, T value) {
                prefixes.put(attribute.getRegistryName().toString(), value);
                return this;
            }

            public <T extends Number> ToolAffixBuilder prefix(VaultGearAttribute<T> attribute, FloatRoll value) {
                prefixes.put(attribute.getRegistryName().toString(), wrapRollWithType(value));
                return this;
            }

            public ToolAffixBuilder prefix(VaultGearAttribute<Integer> attribute, IntRoll value) {
                prefixes.put(attribute.getRegistryName().toString(), wrapRollWithType(value));
                return this;
            }

            private <T extends ISerializable<CompoundTag, JsonObject>> Object wrapRollWithType(T rollObject) {
                JsonObject object = rollObject.writeJson().orElse(null);
                if(object == null) {
                    return new JsonObject();
                }

                if(rollObject instanceof IntRoll intRoll) {
                    object.addProperty("roll_type", "int_roll");
                    if(intRoll instanceof IntRoll.UniformedStep) {
                        object.addProperty("type", "uniformed_step");
                    }
                    if(intRoll instanceof IntRoll.Uniform) {
                        object.addProperty("type", "uniform");
                    }
                    if(intRoll instanceof IntRoll.Constant) {
                        object.addProperty("type", "constant");
                    }
                }
                else if(rollObject instanceof FloatRoll floatRoll) {
                    object.addProperty("roll_type", "float_roll");
                    if(floatRoll instanceof FloatRoll.UniformedStep) {
                        object.addProperty("type", "uniformed_step");
                    }
                    if(floatRoll instanceof FloatRoll.Uniform) {
                        object.addProperty("type", "uniform");
                    }
                    if(floatRoll instanceof FloatRoll.Constant) {
                        object.addProperty("type", "constant");
                    }
                }

                return object;

            }

            public Map<String, Map<String, Object>> build() {
                toolAffixes.put("IMPLICIT", implicits);
                toolAffixes.put("PREFIX", prefixes);
                return toolAffixes;
            }
        }
    }
}
