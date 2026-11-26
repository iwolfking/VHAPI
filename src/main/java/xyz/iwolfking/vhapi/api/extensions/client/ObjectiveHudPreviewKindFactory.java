package xyz.iwolfking.vhapi.api.extensions.client;

import com.mojang.blaze3d.vertex.PoseStack;
import iskallia.vault.client.gui.helper.ObjectiveHudPreview.Kind;
import iskallia.vault.client.gui.screen.options.ui.OptionsCanvas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;
import xyz.iwolfking.vhapi.mixin.accessors.ObjectiveHudPreviewAccessor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectiveHudPreviewKindFactory implements IEnumFactory {
    public static final ObjectiveHudPreviewKindFactory INSTANCE = new ObjectiveHudPreviewKindFactory();

    @FunctionalInterface
    @OnlyIn(Dist.CLIENT)
    public interface PreviewDrawer {
        void draw(OptionsCanvas canvas, int boardSize, float x, float y, float width, float height, long effectiveNow);
    }

    private final Map<Kind, Object> previewDrawers = new IdentityHashMap<>();

    private ObjectiveHudPreviewKindFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return Kind.class.getName();
    }

    public Kind create(String enumName, Class<?>... switchUsers) {
        return appendConstant(enumName, new Object[0], switchUsers);
    }

    public Kind create(ResourceLocation enumId, Class<?>... switchUsers) {
        return create(toInternalName(enumId), switchUsers);
    }

    @OnlyIn(Dist.CLIENT)
    public Kind createWithPreview(String enumName, PreviewDrawer drawer, Class<?>... switchUsers) {
        Objects.requireNonNull(drawer, "drawer");
        Kind created = create(enumName, switchUsers);
        this.previewDrawers.put(created, drawer);
        return created;
    }

    @OnlyIn(Dist.CLIENT)
    public Kind createWithPreview(ResourceLocation enumId, PreviewDrawer drawer, Class<?>... switchUsers) {
        return createWithPreview(toInternalName(enumId), drawer, switchUsers);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerPreview(Kind kind, PreviewDrawer drawer) {
        Objects.requireNonNull(kind, "kind");
        Objects.requireNonNull(drawer, "drawer");
        this.previewDrawers.put(kind, drawer);
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public PreviewDrawer getPreviewDrawer(Kind kind) {
        Object drawer = this.previewDrawers.get(kind);
        return drawer instanceof PreviewDrawer previewDrawer ? previewDrawer : null;
    }

    @OnlyIn(Dist.CLIENT)
    public static float loopedFill(long now) {
        return ObjectiveHudPreviewAccessor.vhapi$loopedFill(now);
    }

    @OnlyIn(Dist.CLIENT)
    public static PoseStack pushBar(OptionsCanvas canvas, float x, float y, float width, float height) {
        return ObjectiveHudPreviewAccessor.vhapi$pushBar(canvas, x, y, width, height);
    }

    @OnlyIn(Dist.CLIENT)
    public static void smallText(OptionsCanvas canvas, String text, float centreX, float centreY, float scale, int rgb) {
        ObjectiveHudPreviewAccessor.vhapi$smallText(canvas, text, centreX, centreY, scale, rgb);
    }

    public Map<Kind, Object> getPreviewDrawers() {
        return Collections.unmodifiableMap(this.previewDrawers);
    }
}