package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.dynamodel.model.item.HandHeldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandheldModelConfig extends Config {

    @Expose
    public List<GsonHandheldModel> MODELS = new ArrayList<>();
    @Override
    public String getName() {
        return "handheld_models";
    }

    @Override
    protected void reset() {

    }


}
