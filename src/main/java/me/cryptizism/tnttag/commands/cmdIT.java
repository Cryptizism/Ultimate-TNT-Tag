package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdIT implements CommandExecutor {
    private final GameManager gameManager;

    public cmdIT(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + "You cannot use this command.");
            return true;
        }
        if(args.length > 0){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                player.sendMessage(ChatColor.RED + "Player does not exist.");
                return true;
            }
            gameManager.itController.addToIT(target, false);
        } else {
            gameManager.itController.addToIT(player, false);
        }
        return true;
    }
}
