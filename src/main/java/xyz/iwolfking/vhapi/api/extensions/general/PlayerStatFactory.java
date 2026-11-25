package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.util.calc.PlayerStat;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

public class PlayerStatFactory implements IEnumFactory {
    public static final PlayerStatFactory INSTANCE = new PlayerStatFactory();

    private PlayerStatFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return PlayerStat.class.getName();
    }

    public PlayerStat create(String enumName, Class<?>... switchUsers) {
        return appendConstant(enumName, new Object[0], switchUsers);
    }

    public PlayerStat create(ResourceLocation enumId, Class<?>... switchUsers) {
        return create(toInternalName(enumId), switchUsers);
    }
}