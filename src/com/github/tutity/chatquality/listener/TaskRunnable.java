package com.github.tutity.chatquality.listener;

import com.github.tutity.chatquality.ChatQuality;
import com.github.tutity.chatquality.ConfigManager;
import com.github.tutity.chatquality.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TaskRunnable implements Runnable {

    private static TaskRunnable Instance;

    public static void init() {
        Instance = new TaskRunnable();
        Bukkit.getScheduler().runTaskTimer(ChatQuality.MainPlugin, Instance, 0, ConfigManager.Instance.config.getInt("Task.Period") * 20);
    }

    @Override
    public void run() {
        for(Player player:Bukkit.getOnlinePlayers()){
            double point = DataManager.Instance.getQualityPoint(player.getName());
            ConfigManager.Instance.TaskDoEvents.stream().filter((cqe) -> (cqe.MinPoint <= point && point < cqe.MaxPoint)).forEachOrdered((cqe) -> {
                cqe.Commands.forEach((cmd) -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", player.getName()));
                });
            });
        }
    }

}
