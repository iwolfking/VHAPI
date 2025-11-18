package xyz.iwolfking.vhapi.mixin.gen;

import iskallia.vault.core.data.key.TemplateKey;
import iskallia.vault.core.world.template.data.DirectTemplateEntry;
import iskallia.vault.core.world.template.data.TemplateEntry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.VHAPI;

@Mixin(value = DirectTemplateEntry.class, remap = false)
public class MixinTemplateKey {

}
