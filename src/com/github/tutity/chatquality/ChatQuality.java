package com.github.tutity.chatquality;

import com.github.tutity.chatquality.listener.ChatListener;
import com.github.tutity.chatquality.listener.TaskRunnable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatQuality extends JavaPlugin {

    public static ChatQuality MainPlugin;

    @Override
    public void onEnable() {
        MainPlugin = this;
        ConfigManager.init();
        DataManager.init();
        LangManager.init();
        ChatListener.init();
        TaskRunnable.init();
        hookToPAPI();
        this.getLogger().info(LangManager.Instance.getText("Plugin.Enabled"));
    }

    @Override
    public void onDisable() {
        DataManager.Instance.save();
        this.getLogger().info(LangManager.Instance.getText("Plugin.Disabled"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandManager.onCommand(sender, command, label, args);
    }

    private void hookToPAPI() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null) {
            boolean success = new PAPIHooker(this).hook();
        }
    }
}
