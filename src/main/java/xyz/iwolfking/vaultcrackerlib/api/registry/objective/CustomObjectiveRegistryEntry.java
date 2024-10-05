package xyz.iwolfking.vaultcrackerlib.api.registry.objective;

import iskallia.vault.config.Config;
import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class CustomObjectiveRegistryEntry extends ForgeRegistryEntry<CustomObjectiveRegistryEntry> {


    private String id;

    private String name;

    Class<? extends CrystalObjective> crystalObjective;


    Supplier<? extends CrystalObjective> crystalObjectiveSupplier;

    SupplierKey<Objective> objectiveKey;

    Class<? extends Objective> objective;


    ItemLike crateItem;

    Config config;
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public ItemLike getCrateItem() {
        return crateItem;
    }


    public SupplierKey<Objective> getKey() {
        return objectiveKey;
    }

    public Class<? extends CrystalObjective> getCrystalObjective() {
        return crystalObjective;
    }

    public Supplier<? extends CrystalObjective> getCrystalObjectiveSupplier() {
        return crystalObjectiveSupplier;
    }




    private CustomObjectiveRegistryEntry(CustomObjectiveBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.crystalObjective = builder.crystalObjective;
        this.crystalObjectiveSupplier = builder.crystalObjectiveSupplier;
        this.objectiveKey = builder.objectiveKey;
        this.objective = builder.objective;
        this.crateItem = builder.crateItem;
        this.config = builder.config;
        this.setRegistryName(id);
    }


    public static class CustomObjectiveBuilder {
        private String id;
        private String name;

        Class<? extends CrystalObjective> crystalObjective;

        Supplier<? extends CrystalObjective> crystalObjectiveSupplier;

        SupplierKey<Objective> objectiveKey;

        Class<? extends Objective> objective;

        ItemLike crateItem;

        Config config;


        public CustomObjectiveBuilder(String id, String name, Class<? extends CrystalObjective> crystalObjective, Supplier<? extends CrystalObjective> crystalObjectiveSupplier, SupplierKey<Objective> objectiveKey, Class<? extends Objective> objective) {
            this.id = id;
            this.name = name;
            this.crystalObjective = crystalObjective;
            this.crystalObjectiveSupplier = crystalObjectiveSupplier;
            this.objectiveKey = objectiveKey;
            this.objective = objective;
        }

        public CustomObjectiveBuilder setCrateItem(ItemLike crate) {
            this.crateItem = crate;
            return this;
        }

        public CustomObjectiveBuilder setConfig(Config config) {
            this.config = config;
            return this;
        }

        public CustomObjectiveRegistryEntry build() {
            return new CustomObjectiveRegistryEntry(this);
        }
    }
}
