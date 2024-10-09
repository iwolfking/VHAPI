package xyz.iwolfking.vhapi.mixin.accessors.configs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "iskallia.vault.config.TrinketConfig$Trinket")
public interface TrinketAccessor {
    @Accessor("trinketConfig")
    void setTrinketConfig(Object config);


}
