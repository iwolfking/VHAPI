package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultGeneralConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGeneralConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVaultBlacklistProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultBlacklistProvider(DataGenerator generator, String modid, String configPath) {
        super(generator, modid, configPath);
    }

    @Override
    public String getName() {
        return modid + " Vault Blacklist Provider";
    }

    public static class VaultBlacklistBuilder {
        private final List<String> ITEM_BLACKLIST = new ArrayList<>();
        private final List<String> BLOCK_BLACKLIST = new ArrayList<>();

        public VaultBlacklistBuilder blacklistItem(String regexString) {
            this.ITEM_BLACKLIST.add(regexString);
            return this;
        }


        public VaultBlacklistBuilder blacklistBlock(String regexString) {
            this.BLOCK_BLACKLIST.add(regexString);
            return this;
        }

        public VaultBlacklistBuilder blacklist(String regexString) {
            blacklistBlock(regexString);
            blacklistItem(regexString);
            return this;
        }

        public VaultGeneralConfig build() {
            VaultGeneralConfig config = new VaultGeneralConfig();
            config.SAVE_PLAYER_SNAPSHOTS = true;
            config.VAULT_EXIT_TNL_MIN = 0.5F;
            config.VAULT_EXIT_TNL_MAX = 1.0F;
            ((VaultGeneralConfigAccessor)config).getBlockBlacklist().addAll(BLOCK_BLACKLIST);
            ((VaultGeneralConfigAccessor)config).getItemBlacklist().addAll(ITEM_BLACKLIST);
            return config;
        }
    }
}
