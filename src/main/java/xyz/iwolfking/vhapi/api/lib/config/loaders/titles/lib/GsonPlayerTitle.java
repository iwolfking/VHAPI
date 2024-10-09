package xyz.iwolfking.vhapi.api.lib.config.loaders.titles.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.AscensionForgeConfig;
import iskallia.vault.config.PlayerTitlesConfig;
import iskallia.vault.init.ModItems;
import iskallia.vault.util.StringUtils;
import iskallia.vault.world.data.PlayerTitlesData;
import xyz.iwolfking.vhapi.api.helpers.workstations.AscensionForgeHelper;

public class GsonPlayerTitle {
    @Expose
    public String id;

    @Expose
    String display;

    @Expose
    String color;

    @Expose
    int cost;

    public PlayerTitlesConfig.Title title() {
        return new PlayerTitlesConfig.Title(cost).put(PlayerTitlesData.Type.CHAT, new PlayerTitlesConfig.Display(display, color)).put(PlayerTitlesData.Type.TAB_LIST, new PlayerTitlesConfig.Display(display, color));
    }

    public AscensionForgeConfig.AscensionForgeListing titleStack(PlayerTitlesConfig.Affix affix) {
        return AscensionForgeHelper.createListing(null, ModItems.TITLE_SCROLL, 1, getNbtStringForType(affix), this.cost);
    }

    private String getNbtStringForType(PlayerTitlesConfig.Affix affix) {
        String affixString = affix.name();
        return "{Affix:\"" + affixString + "\",TitleId:\"" + this.id + "\", display:{Name:\u0027{\"text\":\"" + StringUtils.convertToTitleCase(affix.name().toLowerCase()) + " Title: \", \"extra\":[{\"text\":\"" + this.display + "\",\"color\":\"" + this.color +"\"}]}\u0027}}";
    }

}
