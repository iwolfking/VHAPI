package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedHouseTuning;
import iskallia.vault.config.greed.GreedTraderConfig;
import iskallia.vault.core.world.roll.IntRoll;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GreedTraderConfig.class, remap = false)
public interface GreedTraderConfigAccessor {
    @Accessor("etchingPriceBase")
    @Nullable
    Integer getEtchingPriceBase();

    @Accessor("etchingPriceBase")
    void setEtchingPriceBase(@Nullable Integer etchingPriceBase);

    @Accessor("etchingPricePerTier")
    @Nullable
    Integer getEtchingPricePerTier();

    @Accessor("etchingPricePerTier")
    void setEtchingPricePerTier(@Nullable Integer etchingPricePerTier);

    @Accessor("dripUnlockTier")
    @Nullable
    Integer getDripUnlockTier();

    @Accessor("dripUnlockTier")
    void setDripUnlockTier(@Nullable Integer dripUnlockTier);

    @Accessor("house")
    @Nullable
    GreedHouseTuning getHouse();

    @Accessor("house")
    void setHouse(@Nullable GreedHouseTuning house);

    @Accessor("words")
    Map<String, String> getWordsMutable();

    @Accessor("words")
    void setWords(Map<String, String> words);

    @Accessor("voice")
    Map<String, List<String>> getVoiceMutable();

    @Accessor("voice")
    void setVoice(Map<String, List<String>> voice);

    @Accessor("pools")
    Map<String, List<GreedTraderConfig.PoolEntry>> getPools();

    @Accessor("pools")
    void setPools(Map<String, List<GreedTraderConfig.PoolEntry>> pools);

    @Accessor("roomPoolsByType")
    Map<String, List<GreedTraderConfig.WeightedRoomPool>> getRoomPoolsByType();

    @Accessor("roomPoolsByType")
    void setRoomPoolsByType(Map<String, List<GreedTraderConfig.WeightedRoomPool>> roomPoolsByType);

    @Accessor("modifierPools")
    Map<String, List<GreedTraderConfig.WeightedModifier>> getModifierPools();

    @Accessor("modifierPools")
    void setModifierPools(Map<String, List<GreedTraderConfig.WeightedModifier>> modifierPools);

    @Mixin(value = GreedTraderConfig.PoolEntry.class, remap = false)
    interface PoolEntryAccessor {
        @Accessor("type")
        void setType(String type);

        @Accessor("weight")
        void setWeight(int weight);

        @Accessor("minGreedTier")
        void setMinGreedTier(int minGreedTier);

        @Accessor("maxGreedTier")
        void setMaxGreedTier(int maxGreedTier);

        @Accessor("itemStack")
        void setItemStack(@Nullable ItemStack itemStack);

        @Accessor("catalyst")
        void setCatalyst(@Nullable GreedTraderConfig.CatalystBuilder catalyst);

        @Accessor("inscription")
        void setInscription(@Nullable GreedTraderConfig.InscriptionBuilder inscription);

        @Accessor("crystal")
        void setCrystal(@Nullable GreedTraderConfig.CrystalBuilder crystal);
    }

    @Mixin(value = GreedTraderConfig.CatalystBuilder.class, remap = false)
    interface CatalystBuilderAccessor {
        @Accessor("size")
        void setSize(IntRoll size);

        @Accessor("modifierPool")
        void setModifierPool(@Nullable String modifierPool);

        @Accessor("minModifiers")
        void setMinModifiers(int minModifiers);

        @Accessor("maxModifiers")
        void setMaxModifiers(int maxModifiers);

        @Accessor("isSuper")
        void setSuper(boolean isSuper);
    }

    @Mixin(value = GreedTraderConfig.InscriptionBuilder.class, remap = false)
    interface InscriptionBuilderAccessor {
        @Accessor("rooms")
        void setRooms(List<GreedTraderConfig.RoomEntry> rooms);

        @Accessor("minTime")
        void setMinTime(int minTime);

        @Accessor("maxTime")
        void setMaxTime(int maxTime);

        @Accessor("modifierPool")
        void setModifierPool(@Nullable String modifierPool);

        @Accessor("minModifiers")
        void setMinModifiers(int minModifiers);

        @Accessor("maxModifiers")
        void setMaxModifiers(int maxModifiers);

        @Accessor("isSuper")
        void setSuper(boolean isSuper);

        @Accessor("color")
        void setColor(@Nullable Integer color);
    }

    @Mixin(value = GreedTraderConfig.CrystalBuilder.class, remap = false)
    interface CrystalBuilderAccessor {
        @Accessor("modifierPool")
        void setModifierPool(@Nullable String modifierPool);

        @Accessor("minModifiers")
        void setMinModifiers(int minModifiers);

        @Accessor("maxModifiers")
        void setMaxModifiers(int maxModifiers);

        @Accessor("modifierPoolTwo")
        void setModifierPoolTwo(@Nullable String modifierPoolTwo);

        @Accessor("minModifiersTwo")
        void setMinModifiersTwo(int minModifiersTwo);

        @Accessor("maxModifiersTwo")
        void setMaxModifiersTwo(int maxModifiersTwo);

        @Accessor("modifierPoolThree")
        void setModifierPoolThree(@Nullable String modifierPoolThree);

        @Accessor("minModifiersThree")
        void setMinModifiersThree(int minModifiersThree);

        @Accessor("maxModifiersThree")
        void setMaxModifiersThree(int maxModifiersThree);
    }

    @Mixin(value = GreedTraderConfig.RoomEntry.class, remap = false)
    interface RoomEntryAccessor {
        @Accessor("pool")
        void setPool(@Nullable String pool);

        @Accessor("roomType")
        void setRoomType(@Nullable String roomType);

        @Accessor("count")
        void setCount(int count);

        @Accessor("color")
        void setColor(int color);
    }
}