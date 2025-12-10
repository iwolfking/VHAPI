package xyz.iwolfking.vhapi.integration.wolds;

import iskallia.vault.item.crystal.objective.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import xyz.iwolfking.woldsvaults.init.ModItems;
import xyz.iwolfking.woldsvaults.objectives.*;

import java.util.ArrayList;
import java.util.List;

public class WoldsSealHelper {
    public static ItemStack getSealFromObjective(CrystalObjective objective) {
        List<Component> tooltips = new ArrayList<>();
        objective.addText(tooltips, 0, TooltipFlag.Default.NORMAL, 0, 0);
        if(objective instanceof AlchemyCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_ALCHEMY.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof HauntedBraziersCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_SPIRITS.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof EnchantedElixirCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_ENCHANTER.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof BallisticBingoCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_DOOMSAYER.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof CorruptedCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_CORRUPT.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof BrutalBossesCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_TITAN.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof ZealotCrystalObjective) {
            return ModItems.TOME_OF_TENOS.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof UnhingedScavengerCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_UNHINGED.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof BossCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_WARRIOR.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof ElixirCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_SAGE.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof MonolithCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_SCOUT.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof RoyaleCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_VAULT_ROYALE.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof PvPCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_PVP.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof VaultRuneBossCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_EXECUTIONER.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof BingoCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_PROPHET.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof CakeCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_CAKE.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof HeraldCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_HERALD.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof RaidCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_RAID.getDefaultInstance().setHoverName(tooltips.get(0));
        }
        else if(objective instanceof ScavengerCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_HUNTER.getDefaultInstance().setHoverName(tooltips.get(0));
        }

        return new ItemStack(iskallia.vault.init.ModItems.CRYSTAL_SEAL_EMPTY).setHoverName(tooltips.get(0));
    }
}
