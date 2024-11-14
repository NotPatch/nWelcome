package com.notpatch.nWelcome;

import com.notpatch.nWelcome.command.CommandMain;
import com.notpatch.nWelcome.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NWelcome extends JavaPlugin {

    private static NWelcome instance;

    public boolean useLuckPerms = false;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();
        saveConfig();

        getCommand("nwelcome").setExecutor(new CommandMain());

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        if(getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            getLogger().info("LuckPerms found! Using LuckPerms for group checking.");
            useLuckPerms = true;
        }else{
            useLuckPerms = false;
            getLogger().info("LuckPerms not found! Using default group checking.");
        }
    }

    @Override
    public void onDisable() {

    }

    public static NWelcome getInstance() {
        return instance;
    }


    public boolean isUseLuckPerms() {
        return useLuckPerms;
    }

}
