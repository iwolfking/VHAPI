package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;

@Mixin(value = Config.class, remap = false)
public interface ConfigAccessor {
    @Invoker("getConfigFile")
    File vhapi$getConfigFile();

    @Invoker("onLoad")
    void vhapi$onLoad(Config oldConfigInstance);

    @Invoker("isValid")
    boolean vhapi$isValid();

    @Invoker("reset")
    void vhapi$reset();
}
