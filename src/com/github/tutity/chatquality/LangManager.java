package com.github.tutity.chatquality;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class LangManager {

    public static LangManager Instance;

    public static void init() {
        Instance = new LangManager();
    }
    private final File LangFile;
    private final YamlConfiguration lang;

    public LangManager() {
        LangFile = new File(ChatQuality.MainPlugin.getDataFolder(), "Language.yml");
        if (LangFile.exists() == false) {
            LangFile.getParentFile().mkdirs();
            try {
                LangFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(LangManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lang = YamlConfiguration.loadConfiguration(LangFile);
        if (lang.getInt("Version") != 100) {
            lang.set("Version", 100);
			lang.set("Prefix", "&8[&7素质系统&8] ");
			lang.set("Plugin.Enabled", "插件启用!");
			lang.set("Plugin.Disabled", "插件禁用!");
            lang.set("Command.ChatQuality.Look", "&a素质分: &e%point%");
			lang.set("Helper", Arrays.asList(
				"&8&m------&7&lChatQuality 素质系统&8&m------", 
				"&7插件作者: &8tutity_tiger", 
				"&7/%label% help &8查阅帮助", 
				"&7/%label% look (玩家) &8查阅素质分数", 
				"&7/%label% message <玩家> <信息> &8发送信息", 
				"&7/%label% increase <玩家> <数量> &8加分", 
				"&7/%label% decrease <玩家> <数量> &8减分", 
				"&7/%label% mute <玩家> (秒/-1) &8禁言玩家", 
				"&7/%label% unmute <玩家> &8解禁玩家"
				)
			);
			lang.set("Command.ChatQuality.Look", "&a素质分: &e%point%&a.");
			lang.set("Command.ChatQuality.Message", "&a信息已发送给 &e%player%&a.");
			lang.set("Command.ChatQuality.Increase", "&a已为 &e%player% &a加分 &c%point%&a.");
			lang.set("Command.ChatQuality.Decrease", "&a已为 &e%player% &a扣分 &c%point%&a.");
			lang.set("Command.ChatQuality.Mute", "&a已为 &e%player% &a禁言 &c%time% &a秒.");
			lang.set("Command.ChatQuality.Unmute", "&a已为 &e%player% &a解禁.");
			lang.set("Chat.PermanentlyMuted", "&4你已是永久禁言.");
			lang.set("Chat.TimedMuted", "&a你将在 &e%time% &a秒后解除禁言.");
            save();
        }
    }

    public String getText(String path) {
        return getPrefix() + lang.getString(path).replaceAll("&", "§");
    }
    public String getPrefix() {
    	return lang.getString("Prefix").replaceAll("&", "§");
    }
    public List<String> getHelper() {
        return (List<String>) lang.getList("Helper");
    }
    public void save() {
        try {
            lang.save(LangFile);
        } catch (IOException ex) {
            Logger.getLogger(LangManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
