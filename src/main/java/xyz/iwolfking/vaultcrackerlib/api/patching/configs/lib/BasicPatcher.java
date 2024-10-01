package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib;

public abstract class BasicPatcher {

    public boolean isPatched() {
        return isPatched;
    }

    public void setPatched(boolean isPatched) {
        this.isPatched = isPatched;
    }

    private boolean isPatched = false;




}
