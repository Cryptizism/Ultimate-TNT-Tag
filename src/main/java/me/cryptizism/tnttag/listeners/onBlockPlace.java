package me.cryptizism.tnttag.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import me.cryptizism.tnttag.TntTag;

import org.bukkit.Material;
import org.bukkit.World;
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

    private int secondsToBreak = 1;
    private int ticksToBreak = secondsToBreak * 20 / 8;
    private int maxHeightLimit;

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

        Block against = e.getBlockAgainst();
        if(block.getType() != Material.WOOL) {
            e.setCancelled(true);
            return;
        }
        if(against.getType() == Material.BARRIER){
            e.setCancelled(true);
            trigger.sendMessage("You cannot place a block here.");
            return;
        }
        if(against.getType() == Material.WOOL){
            int y = against.getY() - 1;
            World world = against.getWorld();
            if(world.getBlockAt(against.getX(), y, against.getZ()).getType() == Material.WOOL){
                e.setCancelled(true);
                trigger.sendMessage("You cannot stack up 3 high");
                return;
            }
        }
        if(block.getLocation().getY() > maxHeightLimit){
            e.setCancelled(true);
        }
        new BukkitRunnable(){
            int count = 0;
            final int randId = createID();
            @Override
            public void run(){
                count++;
                if(count > 9){
                    block.setType(Material.AIR);
                    cancel();
                    return;
                }
                blockBreakEffect(trigger, block.getLocation().toVector(), count, randId);
            }
        }.runTaskTimer(TntTag.getInstance(), 0, ticksToBreak);
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

    public void setSecondsToBreak(int secondsToBreak) {
        this.secondsToBreak = secondsToBreak;
    }

    public void setTicksToBreak(int ticksToBreak) {
        this.ticksToBreak = ticksToBreak;
    }
}
