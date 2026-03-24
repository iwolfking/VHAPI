package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TrinketConfig;
import iskallia.vault.gear.trinket.TrinketEffect;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TrinketConfigTrinketAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TrinketEffectConfigAccessor;

import java.util.function.Supplier;

public abstract class AbstractTrinketProvider extends AbstractVaultConfigDataProvider<AbstractTrinketProvider.Builder> {
    protected AbstractTrinketProvider(DataGenerator generator, String modid) {
        super(generator, modid, "trinkets", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Trinkets Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<TrinketConfig> {
        public TrinketConfig.TrinketMap TRINKETS = new TrinketConfig.TrinketMap();

        public Builder() {
            super(TrinketConfig::new);
        }

        public Builder addTrinket(ResourceLocation trinketId, TrinketConfig.Trinket trinket) {
            TRINKETS.put(trinketId, trinket);
            return this;
        }

        @Override
        protected void configureConfig(TrinketConfig config) {
            config.TRINKETS = TRINKETS;
        }

        public static TrinketConfig.Trinket createTrinket(int weight, String name, String effectText, int color, int minUses, int maxUses, int minCraftedUses, int maxCraftedUses, TrinketEffect.Config trinketConfig, String curiosSlot) {
            ((TrinketEffectConfigAccessor)trinketConfig).setCuriosSlot(curiosSlot);
            TrinketConfig.Trinket trinket = new TrinketConfig.Trinket(weight, name, effectText, color, minUses, maxUses);
            ((TrinketConfigTrinketAccessor)trinket).setTrinketConfig(trinketConfig);
            ((TrinketConfigTrinketAccessor)trinket).setMinCraftedUses(minCraftedUses);
            ((TrinketConfigTrinketAccessor)trinket).setMinCraftedUses(maxCraftedUses);
            return trinket;
        }

        public static TrinketConfig.Trinket createTrinket(int weight, String name, String effectText, int minUses, int maxUses, int minCraftedUses, int maxCraftedUses, TrinketEffect.Config trinketConfig, TrinketSlot curiosSlot) {
            return createTrinket(weight, name, effectText, curiosSlot.color, minUses, maxUses, minCraftedUses, maxCraftedUses, trinketConfig, curiosSlot.getSerializedName());
        }

        public enum TrinketSlot implements StringRepresentable {
            BLUE_TRINKET(3304444),
            RED_TRINKET(16534066),
            GREEN_TRINKET(3120128),
            TRINKET(8418936);

            public final int color;

            TrinketSlot(int color) {
                this.color = color;
            }

            @Override
            public @NotNull String getSerializedName() {
                return name().toLowerCase();
            }
        }
    }
}
