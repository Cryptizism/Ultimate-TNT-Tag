package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.manager.GameManager;
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
        gameManager.itController.addToIT(player, false);
        gameManager.mySQLQueries.addPointsToPlayer(player, 5);
        return true;
    }
}
