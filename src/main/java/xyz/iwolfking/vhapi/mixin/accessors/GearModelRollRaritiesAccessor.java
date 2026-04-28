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

    @Accessor("ARMOR_MODEL_ROLLS")
    void setArmorModelRolls(Map<String, List<String>> map);

    @Accessor("SWORD_MODEL_ROLLS")
    public Map<String, List<String>> getSwordModelRolls();

    @Accessor("SWORD_MODEL_ROLLS")
    void setSwordModelRolls(Map<String, List<String>> map);

    @Accessor("AXE_MODEL_ROLLS")
    public Map<String, List<String>> getAxeModelRolls();

    @Accessor("AXE_MODEL_ROLLS")
    void setAxeModelRolls(Map<String, List<String>> map);

    @Accessor("SHIELD_MODEL_ROLLS")
    public Map<String, List<String>> getShieldModelRolls();

    @Accessor("SHIELD_MODEL_ROLLS")
    void setShieldModelRolls(Map<String, List<String>> map);

    @Accessor("WAND_MODEL_ROLLS")
    public Map<String, List<String>> getWandModelRolls();

    @Accessor("WAND_MODEL_ROLLS")
    void setWandModelRolls(Map<String, List<String>> map);

    @Accessor("FOCUS_MODEL_ROLLS")
    public Map<String, List<String>> getFocusModelRolls();

    @Accessor("FOCUS_MODEL_ROLLS")
    void setFocusModelRolls(Map<String, List<String>> map);

    @Accessor("MAGNETS_MODEL_ROLLS")
    public Map<String, List<String>> getMagnetsModelRolls();

    @Accessor("MAGNETS_MODEL_ROLLS")
    void setMagnetsModelRolls(Map<String, List<String>> map);
}
