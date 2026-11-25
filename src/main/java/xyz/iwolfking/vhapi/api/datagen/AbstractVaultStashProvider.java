package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultStashConfig;
import iskallia.vault.config.customisation.CustomisationDiscovery;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultStashConfigAccessor;

public abstract class AbstractVaultStashProvider extends AbstractVaultConfigDataProvider<AbstractVaultStashProvider.Builder> {
    protected AbstractVaultStashProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_stash", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Stash Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultStashConfig> {
        public final List<VaultStashConfig.Category> categories = new ArrayList<>();
        public final List<VaultStashConfig.Ladder> ladders = new ArrayList<>();
        public final List<VaultStashConfig.Breakdown> breakdowns = new ArrayList<>();
        @Nullable
        public Item facetedFocus;
        @Nullable
        public Integer logLines;
        @Nullable
        public Integer logMergeSeconds;
        public final VaultStashConfig.Looks looks = new VaultStashConfig.Looks();

        public Builder() {
            super(VaultStashConfig::new);
        }

        public Builder setFacetedFocus(Item facetedFocus) {
            this.facetedFocus = facetedFocus;
            return this;
        }

        public Builder setLogLines(int logLines) {
            this.logLines = logLines;
            return this;
        }

        public Builder setLogMergeSeconds(int logMergeSeconds) {
            this.logMergeSeconds = logMergeSeconds;
            return this;
        }

        public Builder addCategory(
                String id,
                String name,
                Item icon,
                Consumer<BasicListBuilder<Item>> itemsConsumer,
                Consumer<BasicListBuilder<ResourceLocation>> itemTagsConsumer
        ) {
            VaultStashConfig.Category category = new VaultStashConfig.Category();
            BasicListBuilder<Item> items = new BasicListBuilder<>();
            BasicListBuilder<ResourceLocation> itemTags = new BasicListBuilder<>();
            itemsConsumer.accept(items);
            itemTagsConsumer.accept(itemTags);
            category.id = id;
            category.name = name;
            category.icon = icon;
            category.items = new ArrayList<>(items.build());
            category.itemTags = new ArrayList<>(itemTags.build());
            this.categories.add(category);
            return this;
        }

        public Builder addLadder(Consumer<BasicListBuilder<VaultStashConfig.LadderForm>> formsConsumer) {
            BasicListBuilder<VaultStashConfig.LadderForm> forms = new BasicListBuilder<>();
            formsConsumer.accept(forms);
            VaultStashConfig.Ladder ladder = new VaultStashConfig.Ladder();
            ladder.forms = new ArrayList<>(forms.build());
            this.ladders.add(ladder);
            return this;
        }

        public VaultStashConfig.LadderForm ladderForm(Item item, int factor) {
            VaultStashConfig.LadderForm form = new VaultStashConfig.LadderForm();
            form.item = item;
            form.factor = factor;
            return form;
        }

        public Builder addBreakdown(Item item, List<VaultStashConfig.BreakdownPart> into) {
            VaultStashConfig.Breakdown breakdown = new VaultStashConfig.Breakdown();
            breakdown.item = item;
            breakdown.into = new ArrayList<>(into);
            this.breakdowns.add(breakdown);
            return this;
        }

        public VaultStashConfig.BreakdownPart breakdownPart(Item item, int count) {
            VaultStashConfig.BreakdownPart part = new VaultStashConfig.BreakdownPart();
            part.item = item;
            part.count = count;
            return part;
        }

        public Builder addModelLook(
                String id,
                String name,
                @Nullable String variant,
                String tint,
                @Nullable String sound,
                VaultStashConfig.UnlockType unlockType,
                @Nullable CustomisationDiscovery discovery
        ) {
            VaultStashConfig.Look look = new VaultStashConfig.Look();
            look.id = id;
            look.name = name;
            look.variant = variant;
            look.tint = tint;
            look.sound = sound;
            look.unlock = new VaultStashConfig.Unlock();
            look.unlock.type = unlockType;
            look.unlock.discovery = discovery;
            this.looks.models.add(look);
            return this;
        }

        public Builder addParticleLook(
                String id,
                String name,
                @Nullable String variant,
                String trail,
                VaultStashConfig.UnlockType unlockType,
                @Nullable CustomisationDiscovery discovery
        ) {
            VaultStashConfig.Look look = new VaultStashConfig.Look();
            look.id = id;
            look.name = name;
            look.variant = variant;
            look.trail = trail;
            look.unlock = new VaultStashConfig.Unlock();
            look.unlock.type = unlockType;
            look.unlock.discovery = discovery;
            this.looks.particles.add(look);
            return this;
        }

        @Override
        protected void configureConfig(VaultStashConfig config) {
            VaultStashConfigAccessor accessor = (VaultStashConfigAccessor) config;
            if (!this.categories.isEmpty()) {
                accessor.setCategories(new ArrayList<>(this.categories));
            }
            if (!this.ladders.isEmpty()) {
                accessor.setLadders(new ArrayList<>(this.ladders));
            }
            if (!this.breakdowns.isEmpty()) {
                accessor.setBreakdowns(new ArrayList<>(this.breakdowns));
            }
            if (this.facetedFocus != null) {
                accessor.setFacetedFocus(this.facetedFocus);
            }
            if (this.logLines != null) {
                accessor.setLogLines(this.logLines);
            }
            if (this.logMergeSeconds != null) {
                accessor.setLogMergeSeconds(this.logMergeSeconds);
            }
            if (!this.looks.models.isEmpty() || !this.looks.particles.isEmpty()) {
                VaultStashConfig.Looks copy = new VaultStashConfig.Looks();
                copy.models = new ArrayList<>(this.looks.models);
                copy.particles = new ArrayList<>(this.looks.particles);
                accessor.setLooks(copy);
            }
        }
    }
}