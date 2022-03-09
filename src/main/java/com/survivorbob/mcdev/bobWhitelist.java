package com.survivorbob.mcdev;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class bobWhitelist extends JavaPlugin {

    public static LuckPerms luckperms;
    public static Boolean lp_enabled = false;
    public static String supported_perms_plugins = "LuckPerms";
    public static FileConfiguration config;
    public static boolean enabled = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        config = this.getConfig();
        this.saveDefaultConfig();
        if(config.getBoolean("enable_whitelist"))
        {
            enabled = true;
        }
        getServer().getPluginManager().registerEvents(new com.survivorbob.mcdev.Events.PlayerJoin(), this);
        getServer().getLogger().info("Registered player connection & join handlers.");
        if(getServer().getPluginManager().getPlugin("LuckPerms") != null)
        {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckperms = provider.getProvider();
                lp_enabled = true;
            }
        }
        this.getCommand("wl").setExecutor(new com.survivorbob.mcdev.Commands.wl());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
