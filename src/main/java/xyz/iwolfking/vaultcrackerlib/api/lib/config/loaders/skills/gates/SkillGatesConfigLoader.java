package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.gates;

import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.SkillDescriptionsConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.SkillGatesAccessor;

import java.util.HashMap;
import java.util.Map;

public class SkillGatesConfigLoader extends VaultConfigDataLoader<SkillGatesConfig> {
    public SkillGatesConfigLoader(String namespace) {
        super(new SkillGatesConfig(), "skill/gates", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.SKILL_GATES = CUSTOM_CONFIGS.get(key);
            }
            else if(key.getPath().contains("remove")) {
                for(String gateKey : ((SkillGatesAccessor)CUSTOM_CONFIGS.get(key).getGates()).getEntries().keySet()) {
                    ((SkillGatesAccessor)ModConfigs.SKILL_GATES.getGates()).getEntries().remove(gateKey);
                }
            }
            else {
                for(String gateKey : ((SkillGatesAccessor)CUSTOM_CONFIGS.get(key).getGates()).getEntries().keySet()) {
                    ((SkillGatesAccessor) ModConfigs.SKILL_GATES.getGates()).getEntries().put(gateKey, ((SkillGatesAccessor) CUSTOM_CONFIGS.get(key).getGates()).getEntries().get(gateKey));
                }
            }

        }
    }
}
