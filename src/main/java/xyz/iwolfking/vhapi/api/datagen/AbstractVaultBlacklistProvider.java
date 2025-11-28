package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultGeneralConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGeneralConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractVaultBlacklistProvider extends AbstractVaultConfigDataProvider<AbstractVaultBlacklistProvider.Builder> {
    protected AbstractVaultBlacklistProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/general", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Vault Blacklist Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultGeneralConfig> {
        private final List<String> ITEM_BLACKLIST = new ArrayList<>();
        private final List<String> BLOCK_BLACKLIST = new ArrayList<>();

        public Builder() {
            super(VaultGeneralConfig::new);
        }

        public Builder blacklistItem(String regexString) {
            this.ITEM_BLACKLIST.add(regexString);
            return this;
        }


        public Builder blacklistBlock(String regexString) {
            this.BLOCK_BLACKLIST.add(regexString);
            return this;
        }

        public Builder blacklist(String regexString) {
            blacklistBlock(regexString);
            blacklistItem(regexString);
            return this;
        }

        @Override
        protected void configureConfig(VaultGeneralConfig config) {
            config.SAVE_PLAYER_SNAPSHOTS = true;
            config.VAULT_EXIT_TNL_MIN = 0.5F;
            config.VAULT_EXIT_TNL_MAX = 1.0F;
            ((VaultGeneralConfigAccessor)config).setBlockBlacklist(BLOCK_BLACKLIST);
            ((VaultGeneralConfigAccessor)config).setItemBlacklist(ITEM_BLACKLIST);
        }
    }
}
