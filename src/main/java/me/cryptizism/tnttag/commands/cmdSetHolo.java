package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class cmdSetHolo implements CommandExecutor {
    private final FileConfiguration config;
    private final TntTag plugin;

    public cmdSetHolo(FileConfiguration _config, TntTag _plugin) {
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
        if(args.length == 3){
            try{
                config.set("holo-coords.x", Double.parseDouble(args[0]));
                config.set("spawn-coords.y", Double.parseDouble(args[1]));
                config.set("spawn-coords.z", Double.parseDouble(args[2]));
            } catch (NumberFormatException e){
                player.sendMessage(ChatColor.RED + "Please enter digits.");
            }
        } else if (args.length == 0) {
            config.set("holo-coords.x", player.getLocation().getX());
            config.set("holo-coords.y", player.getLocation().getY());
            config.set("holo-coords.z", player.getLocation().getZ());
        } else {
            player.sendMessage(ChatColor.GREEN + "Either do /set-holo on the block you want to, or do /set-holo [x] [y] [z].");
        }
        plugin.saveConfig();
        return true;
    }
}
