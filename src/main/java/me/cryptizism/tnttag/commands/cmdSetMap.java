package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class cmdSetMap implements CommandExecutor {
    private final FileConfiguration config;
    private final TntTag plugin;

    public cmdSetMap(FileConfiguration _config, TntTag _plugin) {
        this.config = _config;
        this.plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + "You cannot use this command.");
            return true;
        }
        config.set("spawn-coords.x", player.getLocation().getX());
        config.set("spawn-coords.y", player.getLocation().getY());
        config.set("spawn-coords.z", player.getLocation().getZ());
        plugin.saveConfig();
        return true;
    }
}
