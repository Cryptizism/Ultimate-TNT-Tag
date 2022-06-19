package me.cryptizism.tnttag.commands;

import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdSetVariables implements CommandExecutor {
    private final GameManager gameManager;

    public cmdSetVariables(GameManager gameManager){
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
        if(args.length <= 1){
            player.sendMessage(ChatColor.RED + "You need to supply a variable and value");
            return true;
        }
        switch(args[0].toLowerCase()){
            case "maxamount":
                try{
                    gameManager.blockManager.setMaxAmount(Integer.parseInt(args[1]));
                } catch(NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "You need to put a number as a value");
                }
                break;
            case "increaseamount":
                try{
                    gameManager.blockManager.setIncreaseAmount(Integer.parseInt(args[1]));
                } catch(NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "You need to put a number as a value");
                }
                break;
            case "loopticks":
                try{
                    gameManager.blockManager.setLoopTicks(Integer.parseInt(args[1]));
                } catch(NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "You need to put a number as a value");
                }
                break;
            case "roundtime":
                try{
                    gameManager.roundManager.setRoundTime(Integer.parseInt(args[1]));
                } catch(NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "You need to put a number as a value");
                }
                break;
            //case "secondstobreak":
            //    try{
            //        gameManager. (Integer.parseInt(args[1]));
            //    } catch(NumberFormatException e){
            //        player.sendMessage(ChatColor.RED + "You need to put a number as a value");
            //    }
            //    break;
            default:
                player.sendMessage(ChatColor.RED + "You need to provide a valid variable");
            break;
        }
        return true;
    }
}
