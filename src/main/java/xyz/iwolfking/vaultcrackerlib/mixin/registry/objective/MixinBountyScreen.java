package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.client.gui.framework.render.spi.IElementRenderer;
import iskallia.vault.client.gui.framework.render.spi.ITooltipRendererFactory;
import iskallia.vault.client.gui.framework.screen.AbstractElementContainerScreen;
import iskallia.vault.client.gui.screen.bounty.BountyScreen;
import iskallia.vault.container.BountyContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

import java.util.Map;

@Mixin(value = BountyScreen.class, remap = false)
public abstract class MixinBountyScreen extends AbstractElementContainerScreen<BountyContainer> {

    public MixinBountyScreen(BountyContainer container, Inventory inventory, Component title, IElementRenderer elementRenderer, ITooltipRendererFactory<AbstractElementContainerScreen<BountyContainer>> tooltipRendererFactory) {
        super(container, inventory, title, elementRenderer, tooltipRendererFactory);
    }
}