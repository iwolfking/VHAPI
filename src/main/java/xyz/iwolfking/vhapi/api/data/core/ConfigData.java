package xyz.iwolfking.vhapi.api.data.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.data.key.VersionedKey;
import iskallia.vault.core.vault.objective.bingo.BingoItem;
import iskallia.vault.core.vault.objective.elixir.ElixirTask;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.item.ItemPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.core.world.generator.theme.Theme;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.processor.Processor;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.core.world.template.Template;
import iskallia.vault.core.world.template.data.TemplatePool;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class ConfigData {
    public static final Gson CONFIG_LOADER_GSON = Config.GSON;
    public static final Gson GEN_CONFIG_GSON = (new GsonBuilder()).registerTypeHierarchyAdapter(LootTable.class, Adapters.LOOT_TABLE).registerTypeHierarchyAdapter(Palette.class, PaletteAdapter.INSTANCE).registerTypeAdapter(TemplatePool.class, Adapters.TEMPLATE_POOL).registerTypeAdapterFactory(ThemeAdapter.FACTORY).setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
}
