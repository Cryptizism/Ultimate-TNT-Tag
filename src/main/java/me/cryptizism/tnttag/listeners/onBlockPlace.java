package me.cryptizism.tnttag.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import me.cryptizism.tnttag.TntTag;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static com.comphenix.protocol.PacketType.Play.Server.BLOCK_BREAK_ANIMATION;

public class onBlockPlace implements Listener {

    private int idCounter = 0;

    public int createID()
    {
        idCounter++;
        return idCounter;
    }

    @EventHandler
    private void BlockPlaceEvent(BlockPlaceEvent e){
        Player trigger = e.getPlayer();
        Block block = e.getBlock();
        if(block.getType() != Material.WOOL) {
            e.setCancelled(true);
            return;
        }
        if(e.getBlockAgainst().getType() == Material.BARRIER){
            e.setCancelled(true);
            trigger.sendMessage("You cannot place a block here.");
            return;
        }
        new BukkitRunnable(){
            int count = 0;
            final int randId = createID();
            @Override
            public void run(){
                count++;
                if(block == null){
                    cancel();
                    return;
                }
                if(count > 9){
                    block.setType(Material.AIR);
                    cancel();
                    return;
                }
                blockBreakEffect(trigger, block.getLocation().toVector(), count, randId);
            }
        }.runTaskTimer(TntTag.getInstance(), 0, 5);
    }

    public void blockBreakEffect(Player player, Vector vector, int step, int randId) {
        PacketContainer container = new PacketContainer(BLOCK_BREAK_ANIMATION);
        container.getBlockPositionModifier().write(0, new BlockPosition(vector));
        container.getIntegers()
                .write(0, randId)
                .write(1, step);
        execute(player, container);
    }
    void execute(Player receiver, PacketContainer container) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, container);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
