package xyz.iwolfking.vhapi.mixin.gen;

import iskallia.vault.core.world.template.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StructureTemplate.class, remap = false)
public abstract class MixinTemplateKey {

}
