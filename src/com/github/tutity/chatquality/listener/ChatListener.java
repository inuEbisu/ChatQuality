package com.github.tutity.chatquality.listener;

import com.github.tutity.chatquality.LangManager;
import com.github.tutity.chatquality.CQEvent;
import com.github.tutity.chatquality.ChatQuality;
import com.github.tutity.chatquality.ConfigManager;
import com.github.tutity.chatquality.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    private static ChatListener Instance;

    public static void init() {
        Instance = new ChatListener();
        Bukkit.getPluginManager().registerEvents(Instance, ChatQuality.MainPlugin);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (DataManager.Instance.isMuted(player.getName())) {
            event.setCancelled(true);
            int sec = DataManager.Instance.getMutedSecondLeft(player.getName());
            if (sec == -1) {
                player.sendMessage(LangManager.Instance.getText("Chat.PermanentlyMuted"));
            } else {
                player.sendMessage(LangManager.Instance.getText("Chat.TimedMuted").replaceAll("%time%", "" + DataManager.Instance.getMutedSecondLeft(player.getName())));
            }
            return;
        }
        boolean wordchecked = false;
        for (String word : ConfigManager.Instance.EventWords) {
            if (event.getMessage().contains(word)) {
                wordchecked = true;
                event.setMessage(event.getMessage().replaceAll(word, ConfigManager.Instance.config.getString("Event.ReplaceWord")));
            }
        }
        if (wordchecked) {
            DataManager.Instance.setQualityPoint(player.getName(), DataManager.Instance.getQualityPoint(player.getName()) - ConfigManager.Instance.config.getDouble("Event.DecreasePoint"));
            double nowp = DataManager.Instance.getQualityPoint(player.getName());
            for (CQEvent cqe : ConfigManager.Instance.EventDoEvents) {
                if (cqe.MinPoint <= nowp && nowp < cqe.MaxPoint) {
                    for (String cmd : cqe.Commands) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", player.getName()));
                    }
                }
            }
        }
    }
}
