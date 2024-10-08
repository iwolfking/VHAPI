package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.VaultRecyclerConfig;
import iskallia.vault.config.entry.ChanceItemStackEntry;
import net.minecraft.world.item.ItemStack;

public class GsonRecyclerOutput {

    @Expose
    private final GsonChanceItemStack mainOutput;

    @Expose
    private final GsonChanceItemStack extraOutput1;

    @Expose
    private final GsonChanceItemStack extraOutput2;

    public GsonRecyclerOutput(GsonChanceItemStack mainOutput, GsonChanceItemStack extraOutput1, GsonChanceItemStack extraOutput2) {
        this.mainOutput = mainOutput;
        this.extraOutput1 = extraOutput1;
        this.extraOutput2 = extraOutput2;
    }

    public VaultRecyclerConfig.RecyclerOutput getOutput() {
        return new VaultRecyclerConfig.RecyclerOutput(mainOutput.getStack(), extraOutput1.getStack(), extraOutput2.getStack());
    }
}
