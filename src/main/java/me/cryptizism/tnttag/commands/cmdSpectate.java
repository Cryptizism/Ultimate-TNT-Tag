package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdSpectate implements CommandExecutor {
    private final GameManager gameManager;

    public cmdSpectate(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        gameManager.itController.addToSpec(player);
        return true;
    }
}
