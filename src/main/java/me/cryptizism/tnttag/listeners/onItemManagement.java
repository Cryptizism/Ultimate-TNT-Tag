package me.cryptizism.tnttag.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class onItemManagement implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onSlotChange(InventoryClickEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null) return;
        if(item.getType() != Material.COMPASS) return;
        Player player = event.getPlayer();
        Player target = getNearest(player);
        player.setCompassTarget(target.getLocation());
        ItemMeta meta = (ItemMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Tracking " + ChatColor.WHITE + target.getDisplayName());
        List<String> loresList = new ArrayList<String>();
        loresList.add(ChatColor.GREEN + "Left click to track");
        loresList.add(ChatColor.GRAY + "This doesn't update automatically, you need to click to update it");
        meta.setLore(loresList);
        item.setItemMeta(meta);
    }

    public Player getNearest(Player player) {
        double distance = Double.POSITIVE_INFINITY; // To make sure the first
        // player checked is closest
        Player target = null;
        for (Entity entity : player.getNearbyEntities(200, 200, 200)) {
            if (!(entity instanceof Player))
                continue;
            if(entity == player) continue;
            double distanceTo = player.getLocation().distance(entity.getLocation());
            if (distanceTo > distance)
                continue;
            distance = distanceTo;
            target = (Player) entity;
        }
        assert target != null;
        return target;
    }
}
