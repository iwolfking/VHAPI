package xyz.iwolfking.vhapi.api.extensions.gear;

import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public class VaultGearRarityFactory implements IEnumFactory {
    public static final VaultGearRarityFactory INSTANCE = new VaultGearRarityFactory();

    private final Map<VaultGearRarity, SoundEvent> identifySounds = new IdentityHashMap<>();
    private final Map<VaultGearRarity, Integer> identifyPixels = new IdentityHashMap<>();

    private VaultGearRarityFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return VaultGearRarity.class.getName();
    }

    // Declared fields in VaultGearRarity:
    // 0 TextColor -> color
    // 0..8 int    -> armor, weapon, idol, shield, jewel, magnet, wand, focus, voidStone
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, TextColor.class,
                0, int.class,
                1, int.class,
                2, int.class,
                3, int.class,
                4, int.class,
                5, int.class,
                6, int.class,
                7, int.class,
                8, int.class
        };
    }

    public VaultGearRarity create(
            String enumName,
            TextColor color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            @Nullable SoundEvent identifySound,
            @Nullable Integer identifyPixelCount,
            Class<?>... switchUsers
    ) {
        TextColor resolvedColor = Objects.requireNonNull(color, "color");
        VaultGearRarity created = appendConstant(
                enumName,
                new Object[]{
                        resolvedColor,
                        armorModifierCount,
                        weaponModifierCount,
                        idolModifierCount,
                        shieldModifierCount,
                        jewelModifierCount,
                        magnetModifierCount,
                        wandModifierCount,
                        focusModifierCount,
                        voidStoneModifierCount
                },
                switchUsers
        );

        if (identifySound != null) {
            this.identifySounds.put(created, identifySound);
        }
        if (identifyPixelCount != null) {
            this.identifyPixels.put(created, identifyPixelCount);
        }
        return created;
    }

    public VaultGearRarity create(
            String enumName,
            TextColor color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            Class<?>... switchUsers
    ) {
        return create(
                enumName,
                color,
                armorModifierCount,
                weaponModifierCount,
                idolModifierCount,
                shieldModifierCount,
                jewelModifierCount,
                magnetModifierCount,
                wandModifierCount,
                focusModifierCount,
                voidStoneModifierCount,
                null,
                null,
                switchUsers
        );
    }

    public VaultGearRarity create(
            String enumName,
            ChatFormatting color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            @Nullable SoundEvent identifySound,
            @Nullable Integer identifyPixelCount,
            Class<?>... switchUsers
    ) {
        TextColor textColor = TextColor.fromLegacyFormat(Objects.requireNonNull(color, "color"));
        return create(
                enumName,
                textColor,
                armorModifierCount,
                weaponModifierCount,
                idolModifierCount,
                shieldModifierCount,
                jewelModifierCount,
                magnetModifierCount,
                wandModifierCount,
                focusModifierCount,
                voidStoneModifierCount,
                identifySound,
                identifyPixelCount,
                switchUsers
        );
    }

    public VaultGearRarity create(
            String enumName,
            ChatFormatting color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            Class<?>... switchUsers
    ) {
        return create(
                enumName,
                color,
                armorModifierCount,
                weaponModifierCount,
                idolModifierCount,
                shieldModifierCount,
                jewelModifierCount,
                magnetModifierCount,
                wandModifierCount,
                focusModifierCount,
                voidStoneModifierCount,
                null,
                null,
                switchUsers
        );
    }

    public VaultGearRarity create(
            ResourceLocation enumId,
            TextColor color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            @Nullable SoundEvent identifySound,
            @Nullable Integer identifyPixelCount,
            Class<?>... switchUsers
    ) {
        return create(
                toInternalName(enumId),
                color,
                armorModifierCount,
                weaponModifierCount,
                idolModifierCount,
                shieldModifierCount,
                jewelModifierCount,
                magnetModifierCount,
                wandModifierCount,
                focusModifierCount,
                voidStoneModifierCount,
                identifySound,
                identifyPixelCount,
                switchUsers
        );
    }

    public VaultGearRarity create(
            ResourceLocation enumId,
            ChatFormatting color,
            int armorModifierCount,
            int weaponModifierCount,
            int idolModifierCount,
            int shieldModifierCount,
            int jewelModifierCount,
            int magnetModifierCount,
            int wandModifierCount,
            int focusModifierCount,
            int voidStoneModifierCount,
            @Nullable SoundEvent identifySound,
            @Nullable Integer identifyPixelCount,
            Class<?>... switchUsers
    ) {
        return create(
                toInternalName(enumId),
                color,
                armorModifierCount,
                weaponModifierCount,
                idolModifierCount,
                shieldModifierCount,
                jewelModifierCount,
                magnetModifierCount,
                wandModifierCount,
                focusModifierCount,
                voidStoneModifierCount,
                identifySound,
                identifyPixelCount,
                switchUsers
        );
    }

    @Nullable
    public SoundEvent getIdentifySound(VaultGearRarity rarity) {
        return this.identifySounds.get(rarity);
    }

    @Nullable
    public Integer getIdentifyPixels(VaultGearRarity rarity) {
        return this.identifyPixels.get(rarity);
    }

    public Map<VaultGearRarity, SoundEvent> getIdentifySounds() {
        return Collections.unmodifiableMap(this.identifySounds);
    }

    public Map<VaultGearRarity, Integer> getIdentifyPixels() {
        return Collections.unmodifiableMap(this.identifyPixels);
    }
}