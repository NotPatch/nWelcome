package com.notpatch.nWelcome.command;

import com.notpatch.nWelcome.NWelcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandMain implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            if((args[0].equalsIgnoreCase("reload"))){
                if(sender.hasPermission("nwelcome.reload")){
                    NWelcome.getInstance().reloadConfig();
                    NWelcome.getInstance().saveDefaultConfig();
                    NWelcome.getInstance().saveConfig();
                }
            }
        }else{
            if((args[0].equalsIgnoreCase("reload"))){
                NWelcome.getInstance().reloadConfig();
                NWelcome.getInstance().saveDefaultConfig();
                NWelcome.getInstance().saveConfig();
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        switch (args.length){
            case 1:
                return List.of("reload");
        }
        return List.of();
    }
}
