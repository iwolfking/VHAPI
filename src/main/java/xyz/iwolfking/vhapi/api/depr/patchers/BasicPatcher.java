package xyz.iwolfking.vhapi.api.depr.patchers;

@Deprecated
public abstract class BasicPatcher {

    public boolean isPatched() {
        return isPatched;
    }

    public void setPatched(boolean isPatched) {
        this.isPatched = isPatched;
    }

    private boolean isPatched = false;





}
