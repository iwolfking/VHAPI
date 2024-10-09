package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.client.gui.screen.bounty.BountyScreen;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BountyScreen.class, remap = false)
public interface BountyScreenAccessor {

    @Accessor("OBJECTIVE_NAME")
    public static Map<String, TextComponent> getObjectiveNames() {
        return null;
    }

}