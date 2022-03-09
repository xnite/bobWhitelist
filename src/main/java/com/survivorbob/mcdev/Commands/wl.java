package com.survivorbob.mcdev.Commands;

import com.survivorbob.mcdev.bobWhitelist;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;
import java.util.Objects;

import static com.survivorbob.mcdev.bobWhitelist.luckperms;

public class wl implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("bobWhitelist.admin"))
        {
            return false;
        }
        if(args.length <= 0)
        {
            sender.sendMessage(ChatColor.BOLD + "Usage: /wl <add|remove|status|reload> [arguments]");
            return true;
        }
        String playerName = null;
        if(args.length >= 2)
        {
            playerName = args[1].toString();
        }
        User lpUser;
        DataMutateResult result;
        switch(args[0].toString())
        {
            case "reload":
                sender.sendMessage(ChatColor.GREEN + "Reloading Bob's Whitelist configuration.");
                try {
                    Objects.requireNonNull(sender.getServer().getPluginManager().getPlugin("bobWhitelist")).reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Bob's Whitelist configuration reloaded!");
                } catch(NullPointerException npe) {
                    sender.sendMessage(ChatColor.RED + "Bob's Whitelist configuration failed to reload!");
                }

                break;
            case "add":
                if(!bobWhitelist.lp_enabled)
                {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Adding members to server whitelist requires a supported permissions plugin. Currently one of: " + bobWhitelist.supported_perms_plugins);
                }
                if(playerName == null || playerName.isEmpty())
                {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Please provide a player username.");
                    break;
                }
                lpUser = bobWhitelist.luckperms.getUserManager().getUser(playerName.toLowerCase(Locale.ROOT));
                if(lpUser == null)
                {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Could not find player " + playerName + "!");
                    break;
                }
                result = lpUser.data().add(Node.builder("bobWhitelist.whitelisted").build());
                if(result.wasSuccessful())
                {
                    luckperms.getUserManager().saveUser(lpUser);
                    sender.sendMessage(ChatColor.YELLOW + "[Bob's Whitelist]: " + ChatColor.GREEN + "Successfully added " + playerName + " to the server whitelist.");
                } else {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist]: " + ChatColor.RED + "Operation failed!");
                }
                break;
            case "remove":
                if(!bobWhitelist.lp_enabled) {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Removing members from server whitelist requires a supported permissions plugin. Currently one of: " + bobWhitelist.supported_perms_plugins);
                }
                if(playerName == null || playerName.isEmpty())
                {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Please provide a player username.");
                    break;
                }
                lpUser = bobWhitelist.luckperms.getUserManager().getUser(playerName.toLowerCase(Locale.ROOT));
                if(lpUser == null)
                {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist Error]: " + ChatColor.RED + "Could not find player " + playerName + "!");
                    break;
                }
                result = lpUser.data().remove(Node.builder("bobWhitelist.whitelisted").build());
                if(result.wasSuccessful())
                {
                    luckperms.getUserManager().saveUser(lpUser);
                    sender.sendMessage(ChatColor.YELLOW + "[Bob's Whitelist]: " + ChatColor.GREEN + "Successfully removed " + playerName + " from the server whitelist.");
                } else {
                    sender.sendMessage(ChatColor.BOLD + "[Bob's Whitelist]: " + ChatColor.RED + "Operation failed!");
                }
                break;
            case "status":
                if(bobWhitelist.enabled)
                {
                    sender.sendMessage(ChatColor.BLUE + "[Bob's Whitelist]: " + ChatColor.GREEN + "Whitelist is enabled.");
                } else {
                    sender.sendMessage(ChatColor.BLUE + "[Bob's Whitelist]: " + ChatColor.YELLOW + "Whitelist is disabled.");
                }
                break;
            case "enable":
                bobWhitelist.enabled = true;
                sender.sendMessage(ChatColor.BLUE + "[Bob's Whitelist]: " + ChatColor.GREEN + "Whitelist is enabled.");
                break;
            case "disable":
                bobWhitelist.enabled = false;
                sender.sendMessage(ChatColor.BLUE + "[Bob's Whitelist]: " + ChatColor.YELLOW + "Whitelist is disabled.");
                break;
            default:
                sender.sendMessage(ChatColor.BOLD + "Usage: /wl <add|remove|status|reload> [arguments]");
                break;
        }
        return true;
    }
}
