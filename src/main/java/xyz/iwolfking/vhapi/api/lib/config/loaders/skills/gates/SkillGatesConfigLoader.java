package xyz.iwolfking.vhapi.api.lib.config.loaders.skills.gates;

import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.SkillGatesAccessor;

import java.util.HashMap;

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
