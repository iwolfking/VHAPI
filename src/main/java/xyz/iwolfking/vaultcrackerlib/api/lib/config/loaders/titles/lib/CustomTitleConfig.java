package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;

import java.util.List;

public class CustomTitleConfig extends Config {


    @Expose
    public List<GsonPlayerTitle> PREFIXES;

    @Expose
    public List<GsonPlayerTitle> SUFFIXES;

    @Override
    public String getName() {
        return "custom_titles";
    }

    @Override
    protected void reset() {

    }
}
