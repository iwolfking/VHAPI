package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.client.gui.screen.bounty.BountyScreen;
import net.minecraft.network.chat.TextComponent;
import org.checkerframework.checker.units.qual.K;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.iwolfking.vaultcrackerlib.api.data.BountyScreenData;

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
