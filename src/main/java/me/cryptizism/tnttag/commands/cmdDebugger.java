package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.manager.GameManager;
import me.cryptizism.tnttag.manager.ScoreboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdDebugger implements CommandExecutor {
    private final GameManager gameManager;

    public cmdDebugger(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        gameManager.itController.addITPlayer(player);
        return true;
    }
}
