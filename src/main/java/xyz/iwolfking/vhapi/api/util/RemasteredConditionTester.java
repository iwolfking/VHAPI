package xyz.iwolfking.vhapi.api.util;

import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester;

public class RemasteredConditionTester implements ConditionTester
{
    @Override
    public boolean isSatisfied(String mixinClassName)
    {
        return RemasteredHelper.isRemasteredVaultMod();
    }
}
