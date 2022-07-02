package me.cryptizism.tnttag.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChatMessage implements Listener {

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        event.setFormat(ChatColor.WHITE + p.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
    }
}
