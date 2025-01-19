package xyz.iwolfking.vhapi.api.loaders.gear.transmog;

import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.baked.JsonFileBakedModel;
import iskallia.vault.dynamodel.model.item.PlainItemModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlainModelRegistryConfigLoader<T extends DynamicModelRegistry<PlainItemModel>> extends VaultConfigProcessor<HandheldModelConfig> {

    public static final HandheldModelConfig instance = new HandheldModelConfig();

    public static boolean loaded = false;
    T registry;

    Item item;
    public PlainModelRegistryConfigLoader(String namespace, DynamicModelRegistry<PlainItemModel> registry, @NotNull Item item) {
        super(new HandheldModelConfig(), "gear/plain_models/" + item.getRegistryName().getPath());
        this.registry = (T) registry;
        this.item = item;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(HandheldModelConfig config : this.CUSTOM_CONFIGS.values()) {
            for(GsonHandheldModel model : config.MODELS) {
                PlainItemModel plainItemModel = model.getPlainModel();
                if(!registry.containsId(model.getId())) {
                    registry.register(plainItemModel);
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> this.bakeModel(plainItemModel));
                }

            }

        }
    }


    @OnlyIn(Dist.CLIENT)
    public void bakeModel(PlainItemModel handHeldModel) {
        ForgeModelBakery modelLoader = ForgeModelBakery.instance();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        handHeldModel.getAssociatedModelLocations().forEach(modelResourceLocation -> {
            BlockModel unbakedModel = (BlockModel)modelLoader.getModel(modelResourceLocation);
            BakedModel bakedIcon;

            if(ModDynamicModels.jsonModelExists(resourceManager, DynamicModel.prependToId("item/", handHeldModel.getId()))) {
                bakedIcon = unbakedModel.bake(modelLoader, unbakedModel, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, modelResourceLocation, false);;
                bakedIcon = new JsonFileBakedModel(bakedIcon);
            }
            else {
                bakedIcon = handHeldModel.bakeModel(modelResourceLocation, modelLoader, unbakedModel);
            }
            ResourceLocation bakeId = new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath());
            this.registry.bakeIcon(bakeId, bakedIcon);
        });

    }




}
