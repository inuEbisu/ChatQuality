package com.github.tutity.chatquality;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

    public static ConfigManager Instance;
    private File ConfigFile;
    private Gson gson = new Gson();
    public YamlConfiguration config;
    public List<CQEvent> EventDoEvents = new ArrayList();
    public List<CQEvent> TaskDoEvents = new ArrayList();

    public static void init() {
        Instance = new ConfigManager();
    }
    public final List<String> EventWords;
	

    public ConfigManager() {
        ConfigFile = new File(ChatQuality.MainPlugin.getDataFolder(), "config.yml");
        if (ConfigFile.exists() == false) {
            ConfigFile.getParentFile().mkdirs();
            try {
                ConfigFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        config = YamlConfiguration.loadConfiguration(ConfigFile);
        if (config.getInt("Version") != 100) {
            config.set("Version", 100);
            config.set("ChatQuality.DefaultPoint", 100D);
            config.set("ChatQuality.MaxPoint", 100D);
            config.set("Event.Words", Arrays.asList("操你妈", "傻逼"));
            config.set("Event.ReplaceWord", "***");
            config.set("Event.DecreasePoint", 5);
            config.set("Event.DoEvents", Arrays.asList(gson.toJson(new CQEvent(0, 1, Arrays.asList("cq mute %player% -1", "bc %player%疯狂爆粗，被永久禁言了！", "dia message %player% §c小火汁,我早和你说过你这样天天爆粗不行,这下好了,永远说不了话了!"))), gson.toJson(new CQEvent(1, 80, Arrays.asList("cq mute %player% 3600", "bc %player%没有做一个文明的好孩子，被禁言了24小时！", "dia message %player% §c小火汁,不要天天爆粗!这下好了,说不了话了!")))));
            config.set("Task.Period", 5 * 60);
            config.set("Task.DoEvents", Arrays.asList(gson.toJson(new CQEvent(100, 101, Arrays.asList("eco give %player% 5", "dia message %player% &a您的素质分保持在100,已获得5游戏币,继续保持哦～"))), gson.toJson(new CQEvent(0, 100, Arrays.asList("dia increase %player% 1", "dia message %player% §a您的素质分未到100,素质分+1,请继续努力～")))));
            save();
        }
        for (String CQEjson : config.getStringList("Task.DoEvents")) {
            TaskDoEvents.add(gson.fromJson(CQEjson, CQEvent.class));
        }
        config.getStringList("Event.DoEvents").forEach((CQEjson) -> {
            EventDoEvents.add(gson.fromJson(CQEjson, CQEvent.class));
        });
        EventWords = config.getStringList("Event.Words");
    }

    public void save() {
        try {
            config.save(ConfigFile);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
