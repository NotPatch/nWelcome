package com.notpatch.nWelcome.util;

import com.notpatch.nWelcome.NWelcome;
import com.notpatch.nWelcome.message.MessageType;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String getPlayerGroup(Player player) {
        if (NWelcome.getInstance().isUseLuckPerms()) {
            return LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup();
        }else{
            ConfigurationSection configurationSection = NWelcome.getInstance().getConfig().getConfigurationSection("groups");
            for(String group : configurationSection.getKeys(false)){
                String permNode = NWelcome.getInstance().getConfig().getString("groups." + group + ".group-permission");
                if(player.hasPermission(permNode)){
                    return group;
                }
            }
        }
        return null;
    }

    public static String replacePlaceholders(String message, String replaceFrom, String replaceTo) {
        return message.replace(replaceFrom, replaceTo);
    }

    public static List<MessageType> groupMessageTypes(String group) {
        return NWelcome.getInstance().getConfig().getStringList("groups." + group + ".messageType").stream().map(MessageType::valueOf).toList();
    }

    public static List<String> getGroupChatMessages(Player p) {
        List<String> messages = NWelcome.getInstance().getConfig().getStringList("groups." + getPlayerGroup(p) + ".chat");
        List<String> replacedMessages = new ArrayList<>();
        for (String s : messages) {
            s = replacePlaceholders(s, "%group%", getPlayerGroup(p));
            s = replacePlaceholders(s, "%player%", p.getName());
            s = hexColor(s);
            replacedMessages.add(s);
        }
        return replacedMessages;
    }

    public static String[] getGroupTitleMessages(Player p) {
        String line1 = NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(p) + ".title.line-1");
        line1 = replacePlaceholders(line1, "%group%", getPlayerGroup(p));
        line1 = replacePlaceholders(line1, "%player%", p.getName());
        line1 = hexColor(line1);
        String line2 = NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(p) + ".title.line-2");
        line2 = replacePlaceholders(line2, "%group%", getPlayerGroup(p));
        line2 = replacePlaceholders(line2, "%player%", p.getName());
        line2 = hexColor(line2);
        return new String[]{line1, line2};
    }

    public static int getGroupTitleArgs(Player player, String arg){
        return NWelcome.getInstance().getConfig().getInt("groups." + getPlayerGroup(player) + ".title." + arg);
    }

    public static String getGroupActionbar(Player player){
        String actionbar = NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(player) + ".actionbar");
        actionbar = replacePlaceholders(actionbar, "%group%", getPlayerGroup(player));
        actionbar = replacePlaceholders(actionbar, "%player%", player.getName());
        actionbar = hexColor(actionbar);
        return actionbar;
    }

    public static String hexColor(String message) {
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, "" + ChatColor.of(color));
            matcher = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getGroupBossbarMessage(Player player){
        String message = NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(player) + ".bossbar.message");
        message = replacePlaceholders(message, "%group%", getPlayerGroup(player));
        message = replacePlaceholders(message, "%player%", player.getName());
        message = hexColor(message);
        return message;
    }

    public static String getGroupBossbarArgs(Player player, String arg){
        return NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(player) + ".bossbar." + arg);
    }

    public static String getGroupToastArgs(Player player, String arg){
        String message = NWelcome.getInstance().getConfig().getString("groups." + getPlayerGroup(player) + ".toast." + arg);
        message = replacePlaceholders(message, "%group%", getPlayerGroup(player));
        message = replacePlaceholders(message, "%player%", player.getName());
        message = hexColor(message);
        return message;
    }

}
