package xyz.iwolfking.vhapi.mixin.extension_handling;

import iskallia.vault.client.gui.helper.GuiMotion;
import iskallia.vault.client.gui.helper.ObjectiveHudPreview;
import iskallia.vault.client.gui.screen.options.ui.OptionsCanvas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.extensions.client.ObjectiveHudPreviewKindFactory;

@Mixin(value = ObjectiveHudPreview.class, remap = false)
public abstract class MixinObjectiveHudPreview {

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private static void woldsVaults$onDraw(
            OptionsCanvas canvas, ObjectiveHudPreview.Kind kind, int boardSize, float x, float y, float width, float height, long now, CallbackInfo ci
    ) {
        ObjectiveHudPreviewKindFactory.PreviewDrawer drawer =
                ObjectiveHudPreviewKindFactory.INSTANCE.getPreviewDrawer(kind);
        if (drawer != null) {
            long effectiveNow = GuiMotion.isOff() ? 3200L : now;
            drawer.draw(canvas, boardSize, x, y, width, height, effectiveNow);
            ci.cancel();
        }
    }
}