package com.github.tutity.chatquality;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
        	List<String> Helper = LangManager.Instance.getHelper();
        	for (String Help : Helper) {
        		sender.sendMessage(Help.replaceAll("&", "§").replaceAll("%label%", label));
        	}
            return true;
        }
        if (args[0].equalsIgnoreCase("look")) {
            if (args.length == 1) {
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Look").replaceAll("%point%", "" + DataManager.Instance.getQualityPoint(sender.getName())));
            }else{
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Look").replaceAll("%point%", "" + DataManager.Instance.getQualityPoint(args[1])));
            }
        }
        if (sender.hasPermission("ChatQuality.admin")) {
            if (args[0].equalsIgnoreCase("message")) {
                Bukkit.getPlayer(args[1]).sendMessage(LangManager.Instance.getPrefix() + args[2].replaceAll("&", "§"));
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Message").replaceAll("%player%", args[1]));
            }
            if (args[0].equalsIgnoreCase("increase")) {
                DataManager.Instance.setQualityPoint(args[1], DataManager.Instance.getQualityPoint(args[1]) + Double.parseDouble(args[2]));
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Increase").replaceAll("%player%", args[1]).replaceAll("%point%", args[2]));
            }
            if (args[0].equalsIgnoreCase("decrease")) {
                DataManager.Instance.setQualityPoint(args[1], DataManager.Instance.getQualityPoint(args[1]) - Double.parseDouble(args[2]));
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Decrease").replaceAll("%player%", args[1]).replaceAll("%point%", args[2]));
            }
            if (args[0].equalsIgnoreCase("mute")) {
                DataManager.Instance.setMute(args[1], Integer.parseInt(args[2]));
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Mute").replaceAll("%player%", args[1]).replaceAll("%time%", args[2]));
            }
            if (args[0].equalsIgnoreCase("unmute")) {
                DataManager.Instance.setMute(args[1], 0);
                sender.sendMessage(LangManager.Instance.getText("Command.ChatQuality.Unmute").replaceAll("%player%", args[1]));
            }
        }

        return true;
    }
}
