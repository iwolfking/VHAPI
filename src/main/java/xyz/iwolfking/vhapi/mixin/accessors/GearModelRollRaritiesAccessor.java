package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.GearModelRollRaritiesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public interface GearModelRollRaritiesAccessor {

    @Accessor("ARMOR_MODEL_ROLLS")
    public Map<String, List<String>> getArmorModelRolls();

    @Accessor("SWORD_MODEL_ROLLS")
    public Map<String, List<String>> getSwordModelRolls();

    @Accessor("AXE_MODEL_ROLLS")
    public Map<String, List<String>> getAxeModelRolls();

    @Accessor("SHIELD_MODEL_ROLLS")
    public Map<String, List<String>> getShieldModelRolls();

    @Accessor("WAND_MODEL_ROLLS")
    public Map<String, List<String>> getWandModelRolls();

    @Accessor("FOCUS_MODEL_ROLLS")
    public Map<String, List<String>> getFocusModelRolls();
}
