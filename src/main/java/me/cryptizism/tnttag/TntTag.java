package me.cryptizism.tnttag;

import me.cryptizism.tnttag.commands.cmdDebugger;
import me.cryptizism.tnttag.commands.cmdDebugger2;
import me.cryptizism.tnttag.listeners.onDamageDone;
import me.cryptizism.tnttag.listeners.onItemManagement;
import me.cryptizism.tnttag.listeners.onPlayerJoin;
import me.cryptizism.tnttag.listeners.onPlayerLeave;
import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TntTag extends JavaPlugin {

    private GameManager gameManager;
    private static TntTag instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        //set instance
        instance = this;

        this.gameManager = new GameManager(this);

        //Registering Event Listeners
        getServer().getPluginManager().registerEvents(new onDamageDone(gameManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(gameManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerLeave(gameManager), this);
        getServer().getPluginManager().registerEvents(new onItemManagement(), this);
        //Registering Commands
        this.getCommand("it").setExecutor(new cmdDebugger(gameManager));
        this.getCommand("spectate").setExecutor(new cmdDebugger2(gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TntTag getInstance() {
        return instance;
    }
}
