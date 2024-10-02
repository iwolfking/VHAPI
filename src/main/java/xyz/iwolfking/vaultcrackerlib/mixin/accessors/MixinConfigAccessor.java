package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;

@Mixin(Config.class)
public interface MixinConfigAccessor {
    @Invoker("onLoad")
    public abstract void invokeOnLoad(Config oldConfigInstance);

    @Invoker("isValid")
    public abstract boolean invokeIsValid();

    @Invoker("reset")
    public abstract void invokeReset();

    @Invoker("getConfigFile")
    public abstract File invokeGetConfigFile();
}
