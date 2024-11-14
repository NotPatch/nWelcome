package com.notpatch.nWelcome.message;

import com.notpatch.nWelcome.NWelcome;
import com.notpatch.nWelcome.model.Toast;
import com.notpatch.nWelcome.util.MessageUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NMessage {

    public void send(Player player){

        String group = MessageUtil.getPlayerGroup(player);
        List<MessageType> types = MessageUtil.groupMessageTypes(group);

        for(MessageType type : types){
            switch (type){
                case CHAT:
                    for(String s : MessageUtil.getGroupChatMessages(player)){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.sendMessage(s);
                        }
                    }
                    break;
                case TITLE:
                    String line1 = MessageUtil.getGroupTitleMessages(player)[0];
                    String line2 = MessageUtil.getGroupTitleMessages(player)[1];
                    int fadeIn = MessageUtil.getGroupTitleArgs(player, "fadeIn");
                    int stay = MessageUtil.getGroupTitleArgs(player, "stay");
                    int fadeOut = MessageUtil.getGroupTitleArgs(player, "fadeOut");
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendTitle(line1, line2, fadeIn, stay, fadeOut));
                    break;
                case ACTIONBAR:
                    Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtil.getGroupActionbar(player))));
                    break;
                case TOAST:
                    Toast.displayTo(player, MessageUtil.getGroupToastArgs(player, "icon").toLowerCase(), MessageUtil.getGroupToastArgs(player, "title"), Toast.Style.valueOf(MessageUtil.getGroupToastArgs(player, "frame").toUpperCase()));
                    break;
                case BOSSBAR:

                    BossBar bossBar = Bukkit.createBossBar(MessageUtil.getGroupBossbarMessage(player), BarColor.valueOf(MessageUtil.getGroupBossbarArgs(player, "color").toUpperCase()), BarStyle.valueOf(MessageUtil.getGroupBossbarArgs(player, "style").toUpperCase()));
                    bossBar.setProgress(Double.parseDouble(MessageUtil.getGroupBossbarArgs(player, "progress")));
                    Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            bossBar.removeAll();
                        }
                    }.runTaskLater(NWelcome.getInstance(), 20L *Integer.parseInt(MessageUtil.getGroupBossbarArgs(player, "duration")));
                    break;
            }
        }

    }


}
