package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockManager {
    private static GameManager gameManager;

    public BlockManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addBlocksToPlayers(){
        new BukkitRunnable() {
            @Override
            public void run(){
                if(gameManager.gameState != GameState.ACTIVE){
                    cancel();
                }
                for(OfflinePlayer oPlayer :gameManager.itController.PlayersTeamList()){
                    if (!oPlayer.isOnline()) {
                        continue;
                    }
                    Player player = (Player) oPlayer;
                    Inventory inventory = player.getInventory();
                    if (inventory.containsAtLeast(new ItemStack(Material.WOOL), 32)) {
                        continue;
                    }
                    inventory.addItem(new ItemStack(Material.WOOL, 1));
                }
            }
        }.runTaskTimer(TntTag.getInstance(), 0, 20);
    }
}
