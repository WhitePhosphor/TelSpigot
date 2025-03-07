package me.phosphorw.telspigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TelSpigot extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(Bukkit.getPluginCommand("telnet")).setExecutor(new telnetCommand());
        getLogger().info("TelSpigot - A silly telnet client");
        getLogger().info("By WhitePhosphor");
    }

    @Override
    public void onDisable() {
        getLogger().info("See you next time.");
    }

}
