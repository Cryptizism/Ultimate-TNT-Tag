package me.cryptizism.tnttag.listeners;

import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.cryptizism.tnttag.manager.ItController;

public class onDamageDone implements Listener {
    private final GameManager gameManager;

    public onDamageDone(GameManager gameManager){
        this.gameManager = gameManager;

    }

    @EventHandler
    private void onPlayerDamageDone(EntityDamageByEntityEvent event){
        event.setDamage(0);
        Bukkit.broadcastMessage("Hit received");
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return;
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "PvP received");
        Player invoker = (Player) event.getDamager();
        Player attacked = (Player) event.getEntity();
        if ((gameManager.itController.ITTeamList().contains((OfflinePlayer) attacked)) || (gameManager.itController.PlayersTeamList().contains((OfflinePlayer) invoker))) return;
        gameManager.itController.removeITPlayer(invoker);
        gameManager.itController.addITPlayer(attacked);
    }
}
