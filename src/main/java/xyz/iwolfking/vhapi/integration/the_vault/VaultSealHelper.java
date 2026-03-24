package xyz.iwolfking.vhapi.integration.the_vault;

import iskallia.vault.item.crystal.objective.*;
import net.minecraft.world.item.ItemStack;

public class VaultSealHelper
{
    public static ItemStack getSealFromObjective(CrystalObjective objective) {
        if(objective instanceof ElixirCrystalObjective) {
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
