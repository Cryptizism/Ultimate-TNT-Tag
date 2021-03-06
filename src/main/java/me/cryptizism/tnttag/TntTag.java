package me.cryptizism.tnttag;

import me.cryptizism.tnttag.commands.*;
import me.cryptizism.tnttag.listeners.*;
import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class TntTag extends JavaPlugin {

    private GameManager gameManager;
    private static TntTag instance;

    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        //save config
        config.options().copyDefaults(true);
        saveConfig();

        //set instance
        instance = this;

        this.gameManager = new GameManager(this);

        //Registering Event Listeners
        getServer().getPluginManager().registerEvents(new onDamageDone(gameManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(gameManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerLeave(gameManager), this);
        getServer().getPluginManager().registerEvents(new onItemManagement(), this);
        getServer().getPluginManager().registerEvents(new onBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new onFallDamage(), this);
        getServer().getPluginManager().registerEvents(new onBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new onChatMessage(), this);

        //Registering Commands
        this.getCommand("it").setExecutor(new cmdIT(gameManager));
        this.getCommand("start").setExecutor(new cmdForceStart(gameManager));
        this.getCommand("spectate").setExecutor(new cmdSpectate(gameManager));
        this.getCommand("set-spawn").setExecutor(new cmdSetSpawn(config, this));
        this.getCommand("set-map").setExecutor(new cmdSetMap(config, this));
        this.getCommand("set-holo").setExecutor(new cmdSetHolo(config, this));
        this.getCommand("set-variables").setExecutor(new cmdSetVariables(gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        gameManager.close();
    }

    public static TntTag getInstance() {
        return instance;
    }
}
