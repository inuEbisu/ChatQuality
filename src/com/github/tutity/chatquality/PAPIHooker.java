package com.github.tutity.chatquality;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PAPIHooker extends EZPlaceholderHook {

    public PAPIHooker(Plugin plugin) {
        super(plugin, "chatquality");
    }

    @Override
    public String onPlaceholderRequest(Player player, String string) {
        if (string.equals("point")) {
            return DataManager.Instance.getQualityPoint(player.getName()) + "";
        }
        if (string.equals("mute_time")) {
        	if (DataManager.Instance.isMuted(player.getName())) {
                return DataManager.Instance.getMutedSecondLeft(player.getName()) + "";
            }
            return "0";
        }
        return null;
    }

}
