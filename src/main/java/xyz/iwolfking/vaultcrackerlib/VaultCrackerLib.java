package xyz.iwolfking.vaultcrackerlib;

import com.mojang.logging.LogUtils;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGUIConfigPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGroupGUIConfigPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGroupPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchPatcher;

import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultcrackerlib")
public class VaultCrackerLib {

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static SkillStyle TEST_STYLE = new SkillStyle(300, 480, new ResourceLocation("the_vault:gui/research_groups/big_toys"));
    public static ModResearch TEST_RESEARCH = new ModResearch("Draconic Evolution", 1, "quark").withRestrictions(true, false, false, false, true);

    public static ResearchGroup TEST_GROUP = ResearchGroup.builder("Big_Dildos").withResearchNodes("Draconic Evolution").withGroupCostIncrease("Big_Dildos", 2).build();

    public static ResearchGroupStyle TEST_GROUP_STYLE = ResearchGroupStyle.builder("Big_Dildos").withBoxSize(170, 105).withHeaderColor(-44776961).withHeaderTextColor(-1).withPosition(-10, 400).withIcon(new ResourceLocation("the_vault:gui/research_groups/big_toys")).build();



    public VaultCrackerLib() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ResearchPatcher.addResearch(TEST_RESEARCH);
        ResearchGUIConfigPatcher.addResearchGUIStyle("Draconic Evolution", TEST_STYLE);
        ResearchGroupPatcher.addResearchGroup("Big_Dildos", TEST_GROUP);
        ResearchGroupGUIConfigPatcher.addResearchGroupStyle("Big_Dildos", TEST_GROUP_STYLE);
    }
}
