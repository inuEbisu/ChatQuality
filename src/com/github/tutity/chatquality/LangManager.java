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
			lang.set("Prefix", "&8[&7����ϵͳ&8] ");
			lang.set("Plugin.Enabled", "�������!");
			lang.set("Plugin.Disabled", "�������!");
            lang.set("Command.ChatQuality.Look", "&a���ʷ�: &e%point%");
			lang.set("Helper", Arrays.asList(
				"&8&m------&7&lChatQuality ����ϵͳ&8&m------", 
				"&7�������: &8tutity_tiger", 
				"&7/%label% help &8���İ���", 
				"&7/%label% look (���) &8�������ʷ���", 
				"&7/%label% message <���> <��Ϣ> &8������Ϣ", 
				"&7/%label% increase <���> <����> &8�ӷ�", 
				"&7/%label% decrease <���> <����> &8����", 
				"&7/%label% mute <���> (��/-1) &8�������", 
				"&7/%label% unmute <���> &8������"
				)
			);
			lang.set("Command.ChatQuality.Look", "&a���ʷ�: &e%point%&a.");
			lang.set("Command.ChatQuality.Message", "&a��Ϣ�ѷ��͸� &e%player%&a.");
			lang.set("Command.ChatQuality.Increase", "&a��Ϊ &e%player% &a�ӷ� &c%point%&a.");
			lang.set("Command.ChatQuality.Decrease", "&a��Ϊ &e%player% &a�۷� &c%point%&a.");
			lang.set("Command.ChatQuality.Mute", "&a��Ϊ &e%player% &a���� &c%time% &a��.");
			lang.set("Command.ChatQuality.Unmute", "&a��Ϊ &e%player% &a���.");
			lang.set("Chat.PermanentlyMuted", "&4���������ý���.");
			lang.set("Chat.TimedMuted", "&a�㽫�� &e%time% &a���������.");
            save();
        }
    }

    public String getText(String path) {
        return getPrefix() + lang.getString(path).replaceAll("&", "��");
    }
    public String getPrefix() {
    	return lang.getString("Prefix").replaceAll("&", "��");
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
