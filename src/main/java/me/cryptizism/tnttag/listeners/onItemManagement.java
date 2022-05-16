package me.cryptizism.tnttag.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onItemManagement implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onSlotChange(InventoryClickEvent event){
        event.setCancelled(true);
    }

    //@EventHandler
    //public void onInvChange(InventoryPickupItemEvent event){
    //    event.setCancelled(true);
    //}
}
