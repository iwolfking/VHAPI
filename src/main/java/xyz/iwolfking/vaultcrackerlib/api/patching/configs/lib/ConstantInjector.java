package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib;

public class ConstantInjector<T> extends BasicPatcher {
    T REPLACEMENT_VALUE = null;

    public void setConstant(T value) {
        this.REPLACEMENT_VALUE = value;
    }

    public T getConstant() {
        return this.REPLACEMENT_VALUE;
    }
}
