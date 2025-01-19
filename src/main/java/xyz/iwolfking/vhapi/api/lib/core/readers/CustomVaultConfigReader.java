package xyz.iwolfking.vhapi.api.lib.core.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import iskallia.vault.antique.condition.AntiqueCondition;
import iskallia.vault.antique.reward.AntiqueReward;
import iskallia.vault.config.*;
import iskallia.vault.config.adapter.*;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.skillgate.SkillGateType;
import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardScaler;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.key.VersionedKey;
import iskallia.vault.core.vault.objective.bingo.BingoItem;
import iskallia.vault.core.vault.objective.elixir.ElixirTask;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.item.ItemPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.processor.Processor;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.layout.CrystalLayout;
import iskallia.vault.item.crystal.modifiers.CrystalModifiers;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import iskallia.vault.item.crystal.properties.CrystalProperties;
import iskallia.vault.item.crystal.theme.CrystalTheme;
import iskallia.vault.item.crystal.time.CrystalTime;
import iskallia.vault.quest.base.Quest;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.task.Task;
import iskallia.vault.util.EnchantmentCost;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.mixin.accessors.MixinConfigAccessor;

import java.lang.reflect.Type;

public class CustomVaultConfigReader<T extends Config> {

    public static final Gson GSON = ConfigData.CONFIG_LOADER_GSON;

    /**
     *
     * @param name The file name of the datapack file.
     * @param json The JSON data collected from the datapack loader.
     * @param instance The type of class for GSON to use as a reference.
     * @return An instance of the instance class with the data from json.
     */
    public T readCustomConfig(String name, JsonElement json, Type instance) {
        VHAPILoggerUtils.debug(String.format("Reading custom %s config: %s", instance.getTypeName().toLowerCase(), name));
        T readConfig;
        try {
            T config = GSON.fromJson(json, instance);


            ((MixinConfigAccessor)config).invokeOnLoad(config);

            if (!((MixinConfigAccessor)config).invokeIsValid()) {
                VHAPI.LOGGER.error("Invalid config {}, using defaults", this);
                ModConfigs.INVALID_CONFIGS.add(((MixinConfigAccessor)config).invokeGetConfigFile().getName() + " - There was an invalid setting in this config.");
                ((MixinConfigAccessor)config).invokeReset();
            }

            readConfig = config;
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
        return readConfig;
    }

}
