package xyz.iwolfking.vaultcrackerlib.api.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.VaultDiffuserConfig;

import java.util.Map;

public class TestConfig extends CustomVaultConfig {

    @Expose
    public String hello;

    public TestConfig(String root, String extension) {
        super("woldsvaults", extension);
    }

    @Override
    public String getName() {
        return "test";
    }
}
