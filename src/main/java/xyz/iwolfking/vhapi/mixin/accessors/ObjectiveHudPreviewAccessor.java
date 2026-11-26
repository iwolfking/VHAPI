package xyz.iwolfking.vhapi.mixin.accessors;

import com.mojang.blaze3d.vertex.PoseStack;
import iskallia.vault.client.gui.helper.ObjectiveHudPreview;
import iskallia.vault.client.gui.screen.options.ui.OptionsCanvas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ObjectiveHudPreview.class, remap = false)
public interface ObjectiveHudPreviewAccessor {

    @Invoker("loopedFill")
    static float vhapi$loopedFill(long now) {
        throw new AssertionError();
    }

    @Invoker("pushBar")
    static PoseStack vhapi$pushBar(OptionsCanvas canvas, float x, float y, float width, float height) {
        throw new AssertionError();
    }

    @Invoker("smallText")
    static void vhapi$smallText(OptionsCanvas canvas, String text, float centreX, float centreY, float scale, int rgb) {
        throw new AssertionError();
    }
}