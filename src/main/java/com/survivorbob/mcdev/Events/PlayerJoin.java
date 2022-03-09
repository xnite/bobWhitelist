package com.survivorbob.mcdev.Events;

import com.survivorbob.mcdev.bobWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Locale;

public class PlayerJoin implements Listener {
    FileConfiguration config = bobWhitelist.config;
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String playerJoinMessage = config.getString("player_join_message." + p.getName().toLowerCase(Locale.ROOT));
        if(playerJoinMessage != null && !playerJoinMessage.isEmpty())
        {
            /*
             * $name - Player username
             * $display_name - Player display name
             * $ping - Player ping
             */
            event.setJoinMessage(playerJoinMessage.replaceAll("\\$name", p.getName()).replaceAll("\\$display_name", p.getDisplayName()).replaceAll("\\$ping", String.valueOf(p.getPing())));
        }
        return;
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerLoginEvent(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        if(bobWhitelist.enabled && p.hasPermission("bobWhitelist.whitelisted") && !p.isBanned()) {
            if(p.getServer().getOnlinePlayers().size() < p.getServer().getMaxPlayers()) {
                event.allow();
            } else {
                if(p.hasPermission("bobWhitelist.bypass_limit"))
                {
                    event.allow();
                    Bukkit.broadcast(ChatColor.YELLOW + "[Bob's Whitelist]: " + ChatColor.GREEN + event.getPlayer().getName() + " bypassed server max player limit.", "bobWhitelist.notify.bypass");
                } else {
                    event.disallow(PlayerLoginEvent.Result.KICK_FULL, config.getString("full_kick_message"));
                    Bukkit.broadcast(ChatColor.YELLOW + "[Bob's Whitelist]: " + ChatColor.RED + event.getPlayer().getName() + " was kicked because the server is full.", "bobWhitelist.notify.kick_full");
                }
            }
        } else {
            if(bobWhitelist.enabled)
            {
                event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, config.getString("whitelist_kick_message"));
                Bukkit.broadcast(ChatColor.YELLOW + "[Bob's Whitelist]: " + ChatColor.RED + event.getPlayer().getName() + " was kicked because they are not on the whitelist.", "bobWhitelist.notify.kick_whitelist");
            } else {
                event.allow();
            }
        }
        return;
    }
}
