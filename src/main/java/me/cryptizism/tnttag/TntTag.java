package me.cryptizism.tnttag;

import me.cryptizism.tnttag.commands.cmdDebugger;
import me.cryptizism.tnttag.listeners.onDamageDone;
import me.cryptizism.tnttag.listeners.onPlayerJoin;
import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TntTag extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        this.gameManager = new GameManager(this);

        //Registering Event Listeners
        getServer().getPluginManager().registerEvents(new onDamageDone(gameManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(gameManager), this);

        //Registering Commands
        this.getCommand("it").setExecutor(new cmdDebugger(gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
