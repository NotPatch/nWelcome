package com.notpatch.nWelcome.listener;

import com.notpatch.nWelcome.message.NMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        NMessage nMessage = new NMessage();
        nMessage.send(player);

    }

}
