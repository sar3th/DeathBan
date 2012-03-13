package com.github.sar3th.DeathBan;

import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        } else if (command.getName().equalsIgnoreCase("db-ban")) {
            if (args.length == 2) {
                Player victim = getServer().getPlayer(args[0]);
                int banTime = Integer.parseInt(args[1]);
                
                if (!killPlayer(victim.getName(), sender.getName()))
                    sender.sendMessage(String.format("Unable to kill %s", victim.getName()));
                
                // Add to banlist regardless
                banPlayer(victim.getName(), banTime, sender.getName());
                
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private boolean killPlayer(String victimName, String killerName) {
        Player victim = getServer().getPlayer(victimName);
        
        if (victim != null) {
            if (!victim.isDead()) {
                victim.damage(victim.getHealth());
                getLogger().log(Level.INFO, String.format("%s killed by %s", victim.getName(), killerName));
            } else {
                getLogger().log(Level.INFO, String.format("%s wanted to kill %s but player was already dead", killerName, victim.getName()));
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    private void banPlayer(String playerName, int banDuration) {
        banPlayer(playerName, banDuration, null);
    }
    
    private void banPlayer(String playerName, int banDuration, String senderName) {
        if (senderName == null) {
            // Player was banned for death
            getLogger().log(Level.INFO, String.format("%s banned for %d seconds", playerName, banDuration));
        } else {
            getLogger().log(Level.INFO, String.format("%s banned %s for %d seconds", playerName, senderName, banDuration));
        }
        // TODO: Add kick and ban code here
    }
}
