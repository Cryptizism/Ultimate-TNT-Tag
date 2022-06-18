package me.cryptizism.tnttag.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlockBreak implements Listener {
    @EventHandler
    private void BlockBreakEvent(BlockBreakEvent e){
        e.setCancelled(true);
    }
}
