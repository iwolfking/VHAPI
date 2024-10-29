package xyz.iwolfking.vhapi.api.loaders.skills.gates;

import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.SkillGatesAccessor;

public class SkillGatesConfigLoader extends VaultConfigProcessor<SkillGatesConfig> {
    public SkillGatesConfigLoader() {
        super(new SkillGatesConfig(), "skill/gates");
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
        super.afterConfigsLoad(event);
    }
}
