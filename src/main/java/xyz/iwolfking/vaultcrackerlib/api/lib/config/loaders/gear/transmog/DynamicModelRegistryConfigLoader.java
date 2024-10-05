package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DynamicModelPropertiesAccessor;

import java.util.HashMap;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DynamicModelRegistryConfigLoader<T extends DynamicModelRegistry<HandHeldModel>> extends VaultConfigDataLoader<HandheldModelConfig> {

    public static final HandheldModelConfig instance = new HandheldModelConfig();

    T registry;

    Item item;
    public DynamicModelRegistryConfigLoader(String namespace, DynamicModelRegistry<HandHeldModel> registry, @NotNull Item item) {
        super(instance, "gear/handheld_models/" + item.getRegistryName().getPath(), new HashMap<>(), namespace);
        this.registry = (T) registry;
        this.item = item;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(HandheldModelConfig config : this.CUSTOM_CONFIGS.values()) {
            for(GsonHandheldModel model : config.MODELS) {
                DynamicModelProperties properties = new DynamicModelProperties();
                ((DynamicModelPropertiesAccessor)properties).setDiscoverOnRoll(model.isDiscoverOnRoll());
                ((DynamicModelPropertiesAccessor)properties).setAllowTransmogrification(model.isAllowTransmogrification());
                HandHeldModel handHeldModel = new HandHeldModel(model.getId(), model.getDisplayName()).properties(properties);
                registry.register(handHeldModel);
                if(Dist.CLIENT.isClient()) {
                    this.bakeModel(handHeldModel);
                }
            }

        }
        this.CUSTOM_CONFIGS.clear();
    }

    @OnlyIn(Dist.CLIENT)
    public void bakeModel(HandHeldModel handHeldModel) {
        ForgeModelBakery modelLoader = ForgeModelBakery.instance();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        BlockModel unbakedModel = (BlockModel)modelLoader.getModel(DynamicModel.prependToId("item/", handHeldModel.getId()));
        BakedModel bakedIcon;
        if(ModDynamicModels.jsonModelExists(resourceManager, DynamicModel.prependToId("item/", handHeldModel.getId()))) {
            bakedIcon = unbakedModel.bake(modelLoader, unbakedModel, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, DynamicModel.prependToId("item/", handHeldModel.getId()), false);
        }
        else {
            bakedIcon = handHeldModel.bakeModel((ModelResourceLocation) DynamicModel.prependToId("item/", handHeldModel.getId()), modelLoader, unbakedModel);
        }
        ResourceLocation bakeId = new ResourceLocation(handHeldModel.getId().getNamespace(), handHeldModel.getId().getPath());
        this.registry.bakeIcon(bakeId, bakedIcon);
    }



}
