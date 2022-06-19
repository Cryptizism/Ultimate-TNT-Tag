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

    public int maxAmount = 32;
    public int increaseAmount = 1;
    public int loopTicks = 20; //How many ticks till next loop

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setIncreaseAmount(int increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    public void setLoopTicks(int loopTicks) {
        this.loopTicks = loopTicks;
    }

    public void addBlocksToPlayers(){
        new BukkitRunnable() {
            @Override
            public void run(){
                if(gameManager.gameState != GameState.ACTIVE){
                    cancel();
                }
                for(OfflinePlayer oPlayer :gameManager.itController.InGameList()){
                    if (!oPlayer.isOnline()) {
                        continue;
                    }
                    Player player = (Player) oPlayer;
                    Inventory inventory = player.getInventory();
                    if (inventory.containsAtLeast(new ItemStack(Material.WOOL), maxAmount)) {
                        continue;
                    }
                    inventory.addItem(new ItemStack(Material.WOOL, increaseAmount));
                }
            }
        }.runTaskTimer(TntTag.getInstance(), 0, loopTicks);
    }
}
