package xyz.iwolfking.vhapi.api.config;

import iskallia.vault.config.Config;

public class AttributeCapConfig extends Config {

    public float luckyHitCap;
    public float aoeCap;
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
