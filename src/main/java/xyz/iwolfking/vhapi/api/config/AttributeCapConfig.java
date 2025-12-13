package xyz.iwolfking.vhapi.api.config;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;

public class AttributeCapConfig extends Config {

    @Expose
    public float luckyHitCap;

    @Expose
    public float aoeCap;

    @Expose
    public boolean enableOverrides;

    @Override
    public String getName() {
        return "attribute_cap_overrides";
    }

    @Override
    protected void reset() {
        this.luckyHitCap = 0.562F;
        this.aoeCap = 0.80F;
        this.enableOverrides = false;
    }
}
