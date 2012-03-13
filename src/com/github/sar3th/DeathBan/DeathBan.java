package com.github.sar3th.DeathBan;

import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of DeathBan plugin for Bukkit
 */
public class DeathBan extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register as event Listener
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (isEnabled()) {
            // TODO: Add code to kick player if he's banned
        }
    }
    
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (isEnabled()) {
            // TODO: Add code to detect player death and add a ban
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("db-settime")) {
            if (args.length == 1) {
                int banTime = Integer.parseInt(args[0]);
                getConfig().set("bantime", banTime);
                getLogger().log(Level.INFO, String.format("%s set bantime to %d", sender.getName(), banTime));
                saveConfig();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
