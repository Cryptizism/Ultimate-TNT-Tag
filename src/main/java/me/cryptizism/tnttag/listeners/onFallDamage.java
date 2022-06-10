package me.cryptizism.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onFallDamage implements Listener {
    @EventHandler
    private void onDamageEvent(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            e.setCancelled(true);
        }
    }
}
