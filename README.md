# Bob's Whitelist plugin for Spigot/PaperMC
This is a permissions based whitelist plugin. It uses permissions (such as LuckPerms) to determine if connecting players are allowed to join.

You can use this plugin to customize the whitelist kick message, full kick message, bypass full kick, and display custom join messages for players.

# Command
All commands are sub of the `/wl` command (eg- `/wl add notch` or `/wl remove notch`)

## Add/Remove
Adding and removing players on whitelist requires a supported permissions plugin (right now, LuckPerms).

`/wl add <player>` - Adds a player to whitelist.
`/wl remove <player>` - Removes player from whitelist.

## Status
`/wl status` - Reports back whether the whitelist is enabled or not.

## Enable/Disable
`/wl enable` - Enables whitelist, overriding configuration until next restart.
`/wl disable` - Disables whitelist, overriding configuration until next restart.