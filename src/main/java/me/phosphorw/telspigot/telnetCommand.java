package me.phosphorw.telspigot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class telnetCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("telnet")) {
            Telnet cli = new Telnet();

            if (args.length == 0) {
                sender.sendMessage("Usage:" + "\n" +
                        "connect: /telnet connect <addr>" + "\n" +
                        "command: /telnet command <command>" + "\n" +
                        "disconnect: /telnet disconnect" + "\n");
                return true;
            }

            if (args.length == 2 && args[0].equals("connect") || args.length == 1 && args[0].equals("connect")) {
                int port = ((args.length >= 2) ? Integer.parseInt(args[1]) : 23);
                try {
                    cli.Connect(args[0], port, sender);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong..");
                }
                return true;
            }

            if (args.length == 2 && args[0].equals("command")) {
                try {
                    cli.sendCommand(args[1], sender);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong..");
                }
                return true;
            }

            if (args.length == 1 && args[0].equals("disconnect")) {
                try {
                    cli.Disconnect(sender);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong..");
                }
                return true;
            }
        }
        return false;
    }
}
