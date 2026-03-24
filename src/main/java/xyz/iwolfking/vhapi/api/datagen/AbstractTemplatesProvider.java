package xyz.iwolfking.vhapi.api.datagen;

import net.minecraft.data.DataGenerator;

public abstract class AbstractTemplatesProvider extends AbstractVaultConfigDataProvider {
    protected AbstractTemplatesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "templates", null);
    }

    public abstract void registerConfigs();


    @Override
    public String getName() {
        return modid + " Templates Data Provider";
    }


}
