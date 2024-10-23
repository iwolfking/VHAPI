package xyz.iwolfking.vhapi.mixin.registry.objective;

import iskallia.vault.client.gui.screen.bounty.BountyScreen;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.iwolfking.vhapi.api.data.api.BountyScreenData;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = BountyScreen.class, remap = false)
public class MixinBountyScreen {
    @Shadow @Final @Mutable
    public static Map<String, TextComponent> OBJECTIVE_NAME;

    static {
        OBJECTIVE_NAME = new HashMap<>();
        OBJECTIVE_NAME.putAll(BountyScreenData.OBJECTIVE_NAME);
    }
}
