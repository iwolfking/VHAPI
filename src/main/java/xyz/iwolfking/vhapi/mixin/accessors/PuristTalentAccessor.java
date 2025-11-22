package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.data.adapter.array.ArrayAdapter;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.skill.talent.type.PuristTalent;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(value = PuristTalent.class, remap = false)
public interface PuristTalentAccessor {
    @Accessor("rarities")
    Set<VaultGearRarity> getRarities();
    @Accessor("slots")
    Set<EquipmentSlot> getSlots();
    @Accessor("damageIncrease")
    float getDamageIncrease();

    @Accessor("rarities")
    void  setRarities(Set<VaultGearRarity> rarities);

    @Accessor("slots")
    void setSlots(Set<EquipmentSlot> slots);

    @Accessor("damageIncrease")
    void setDamageIncrease(float increase);

}
