package me.cryptizism.tnttag.listeners;

import me.cryptizism.tnttag.manager.GameManager;
import me.cryptizism.tnttag.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onDamageDone implements Listener {
    private final GameManager gameManager;

    public onDamageDone(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    private void onPlayerDamageDone(EntityDamageByEntityEvent event){
        if(gameManager.gameState == GameState.LOBBY){
            event.setCancelled(true);
        }
        event.setDamage(0);
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return;
        Player invoker = (Player) event.getDamager();
        Player attacked = (Player) event.getEntity();
        if ((gameManager.itController.ITTeamList().contains((OfflinePlayer) attacked)) || (gameManager.itController.PlayersTeamList().contains((OfflinePlayer) invoker))) return;
        gameManager.itController.addToPlayer(invoker);
        gameManager.itController.addToIT(attacked, false);
        invoker.sendMessage(ChatColor.GREEN + "You tagged " + attacked.getName() + ChatColor.GREEN + "!");
        attacked.sendMessage(ChatColor.RED + invoker.getName() + " tagged you!");
    }
}
