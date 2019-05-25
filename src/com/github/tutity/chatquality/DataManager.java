package com.github.tutity.chatquality;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager implements Runnable {

    public static DataManager Instance;

    public static void init() {
        Instance = new DataManager();
        Bukkit.getScheduler().runTaskTimer(ChatQuality.MainPlugin, Instance, 20, 20);
    }
    private final File DataFile;
    public final YamlConfiguration data;
    public HashMap<String, Integer> MutedPlayers = new HashMap();

    public DataManager() {
        DataFile = new File(ChatQuality.MainPlugin.getDataFolder(), "Data.yml");
        if (DataFile.exists() == false) {
            DataFile.getParentFile().mkdirs();
            try {
                DataFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data = YamlConfiguration.loadConfiguration(DataFile);
        if (data.getInt("Version") != 100) {
            data.set("Version", 100);
            data.set("MutedPlayers", Arrays.asList(new HashMap()));
        }
        MutedPlayers = (HashMap<String, Integer>) data.getMapList("MutedPlayers").get(0);
    }

    public void save() {
        try {
            data.save(DataFile);
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.set("MutedPlayers", Arrays.asList(MutedPlayers));
    }

    public double getQualityPoint(String pn) {
        if (data.contains("QualityPoint." + pn) == false) {
            data.set("QualityPoint." + pn, ConfigManager.Instance.config.getDouble("ChatQuality.DefaultPoint"));
        }
        return data.getDouble("QualityPoint." + pn);
    }

    public void setQualityPoint(String pn, double point) {
    	double MaxPoint = ConfigManager.Instance.config.getDouble("ChatQuality.MaxPoint");
    	double MinPoint = ConfigManager.Instance.config.getDouble("ChatQuality.MinPoint");
        if (point > MaxPoint) {
            data.set("QualityPoint." + pn, MaxPoint);
        } else if (point < MinPoint) {
            data.set("QualityPoint." + pn, MinPoint);
        } else {
            data.set("QualityPoint." + pn, point);
        }
    }

    public void setMute(String pn, Integer sec) {
        MutedPlayers.put(pn, sec);
    }

    public boolean isMuted(String pn) {
        return MutedPlayers.containsKey(pn);
    }

    public int getMutedSecondLeft(String pn) {
        return MutedPlayers.get(pn);
    }

    @Override
    public void run() {
        for (String pn : MutedPlayers.keySet()) {
            if (MutedPlayers.get(pn) > 0) {
                MutedPlayers.put(pn, MutedPlayers.get(pn) - 1);
            } else if (MutedPlayers.get(pn) == 0) {
                MutedPlayers.remove(pn);
            }
        }
    }
}
