package xyz.iwolfking.vhapi.api.extensions.client;

import iskallia.vault.block.VaultCrateBlock;
import iskallia.vault.client.gui.helper.ObjectiveHudPreview;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ObjectiveSealLookFactory implements IEnumFactory {
    public static final ObjectiveSealLookFactory INSTANCE = new ObjectiveSealLookFactory();

    private static final String TARGET_CLASS = "iskallia.vault.client.gui.screen.block.crystal.ObjectiveSealLook";

    private ObjectiveSealLookFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return TARGET_CLASS;
    }

    // Declared fields in ObjectiveSealLook:
    // 0 Item                      -> seal
    // 0 Supplier                  -> objective
    // 0 String                    -> objectiveName
    // 0 VaultCrateBlock.Type      -> crate
    // 0 ObjectiveHudPreview.Kind  -> preview
    // 0 int                       -> boardSize
    // 1 String                    -> description
    // 2 String                    -> rewardsNote
    // 1 int                       -> rgb
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, Item.class,
                0, Supplier.class,
                0, String.class,
                0, VaultCrateBlock.Type.class,
                0, ObjectiveHudPreview.Kind.class,
                0, int.class,
                1, String.class,
                2, String.class,
                1, int.class
        };
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> E create(
            String enumName,
            Item seal,
            Supplier<CrystalObjective> objective,
            String objectiveName,
            VaultCrateBlock.Type crate,
            ObjectiveHudPreview.Kind preview,
            int boardSize,
            String description,
            @Nullable String rewardsNote,
            Class<?>... switchUsers
    ) {
        Item resolvedSeal = Objects.requireNonNull(seal, "seal");
        Supplier<CrystalObjective> resolvedObjective = Objects.requireNonNull(objective, "objective");
        String resolvedObjectiveName = Objects.requireNonNull(objectiveName, "objectiveName");
        VaultCrateBlock.Type resolvedCrate = Objects.requireNonNull(crate, "crate");
        ObjectiveHudPreview.Kind resolvedPreview = Objects.requireNonNull(preview, "preview");
        String resolvedDescription = Objects.requireNonNull(description, "description");

        return (E) appendConstant(
                enumName,
                new Object[]{
                        resolvedSeal,
                        resolvedObjective,
                        resolvedObjectiveName,
                        resolvedCrate,
                        resolvedPreview,
                        boardSize,
                        resolvedDescription,
                        rewardsNote,
                        -1
                },
                switchUsers
        );
    }

    public <E extends Enum<E>> E create(
            String enumName,
            Item seal,
            Supplier<CrystalObjective> objective,
            String objectiveName,
            VaultCrateBlock.Type crate,
            ObjectiveHudPreview.Kind preview,
            int boardSize,
            String description,
            Class<?>... switchUsers
    ) {
        return create(
                enumName,
                seal,
                objective,
                objectiveName,
                crate,
                preview,
                boardSize,
                description,
                null,
                switchUsers
        );
    }

    public <E extends Enum<E>> E create(
            ResourceLocation enumId,
            Item seal,
            Supplier<CrystalObjective> objective,
            String objectiveName,
            VaultCrateBlock.Type crate,
            ObjectiveHudPreview.Kind preview,
            int boardSize,
            String description,
            @Nullable String rewardsNote,
            Class<?>... switchUsers
    ) {
        return create(
                toInternalName(enumId),
                seal,
                objective,
                objectiveName,
                crate,
                preview,
                boardSize,
                description,
                rewardsNote,
                switchUsers
        );
    }

    public <E extends Enum<E>> E create(
            ResourceLocation enumId,
            Item seal,
            Supplier<CrystalObjective> objective,
            String objectiveName,
            VaultCrateBlock.Type crate,
            ObjectiveHudPreview.Kind preview,
            int boardSize,
            String description,
            Class<?>... switchUsers
    ) {
        return create(
                toInternalName(enumId),
                seal,
                objective,
                objectiveName,
                crate,
                preview,
                boardSize,
                description,
                null,
                switchUsers
        );
    }
}