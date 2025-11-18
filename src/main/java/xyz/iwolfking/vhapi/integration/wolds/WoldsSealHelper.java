package xyz.iwolfking.vhapi.integration.wolds;

import iskallia.vault.item.crystal.objective.*;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.woldsvaults.init.ModItems;
import xyz.iwolfking.woldsvaults.objectives.*;

public class WoldsSealHelper {
    public static ItemStack getSealFromObjective(CrystalObjective objective) {
        if(objective instanceof AlchemyCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_ALCHEMY.getDefaultInstance();
        }
        else if(objective instanceof HauntedBraziersCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_SPIRITS.getDefaultInstance();
        }
        else if(objective instanceof EnchantedElixirCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_ENCHANTER.getDefaultInstance();
        }
        else if(objective instanceof BallisticBingoCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_DOOMSAYER.getDefaultInstance();
        }
        else if(objective instanceof CorruptedCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_CORRUPT.getDefaultInstance();
        }
        else if(objective instanceof BrutalBossesCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_TITAN.getDefaultInstance();
        }
        else if(objective instanceof ZealotCrystalObjective) {
            return ModItems.TOME_OF_TENOS.getDefaultInstance();
        }
        else if(objective instanceof UnhingedScavengerCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_UNHINGED.getDefaultInstance();
        }
        else if(objective instanceof BossCrystalObjective) {
            return ModItems.CRYSTAL_SEAL_WARRIOR.getDefaultInstance();
        }
        else if(objective instanceof ElixirCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_SAGE.getDefaultInstance();
        }
        else if(objective instanceof MonolithCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_SCOUT.getDefaultInstance();
        }
        else if(objective instanceof RoyaleCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_VAULT_ROYALE.getDefaultInstance();
        }
        else if(objective instanceof PvPCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_PVP.getDefaultInstance();
        }
        else if(objective instanceof VaultRuneBossCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_EXECUTIONER.getDefaultInstance();
        }
        else if(objective instanceof BingoCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_PROPHET.getDefaultInstance();
        }
        else if(objective instanceof CakeCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_CAKE.getDefaultInstance();
        }
        else if(objective instanceof HeraldCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_HERALD.getDefaultInstance();
        }
        else if(objective instanceof RaidCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_RAID.getDefaultInstance();
        }
        else if(objective instanceof ScavengerCrystalObjective) {
            return iskallia.vault.init.ModItems.CRYSTAL_SEAL_HUNTER.getDefaultInstance();
        }

        return new ItemStack(iskallia.vault.init.ModItems.CRYSTAL_SEAL_EMPTY);
    }
}
