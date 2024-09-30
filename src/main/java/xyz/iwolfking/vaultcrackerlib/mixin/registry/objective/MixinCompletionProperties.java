package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.bounty.task.properties.CompletionProperties;
import iskallia.vault.bounty.task.properties.TaskProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

import java.util.List;

@Mixin(value = CompletionProperties.class, remap = false)
public class MixinCompletionProperties extends TaskProperties {
    @Shadow
    private String id;

    protected MixinCompletionProperties(ResourceLocation taskType, List<ResourceLocation> validDimensions, boolean vaultOnly, double amount) {
        super(taskType, validDimensions, vaultOnly, amount);
    }


    @Inject(method = "deserializeNBT(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void addCustomVaultObjectives(CompoundTag tag, CallbackInfo ci) {
        for(CustomVaultObjectiveEntry entry : CustomVaultObjectiveRegistry.getCustomVaultObjectiveEntries()) {
            if(this.id.equals("the_vault:" + entry.id())) {
                this.id = entry.id();
            }
        }
    }
}
