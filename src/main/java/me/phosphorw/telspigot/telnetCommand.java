package me.phosphorw.telspigot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class telnetCommand implements CommandExecutor {

    HashMap<String, Telnet> playerCli = new HashMap<>();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("telnet")) {
            if (args.length == 0) {
                sender.sendMessage("Usage:" + "\n" +
                        "connect: /telnet connect <addr>" + "\n" +
                        "command: /telnet command <command>" + "\n" +
                        "disconnect: /telnet disconnect" + "\n" +
                        "delete: /telnet delete : entirely delete your existing telnet client" + "\n");
                return true;
            }

            if (args.length >= 2 && args[0].equals("connect") || args.length == 1 && args[0].equals("connect")) {
                Telnet cli = playerCli.computeIfAbsent(sender.getName(), k -> new Telnet());
                int port = ((args.length == 3) ? Integer.parseInt(args[1]) : 23);
                try {
                    cli.Connect(args[0], port);
                    sender.sendMessage("Successfully connected to " + args[0] + " on port" + port + ".");
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong. Is the host address or port number is correct?");
                }
                return true;
            }

            if (args.length == 2 && args[0].equals("command")) {
                Telnet cli = playerCli.computeIfAbsent(sender.getName(), k -> new Telnet());
                try {
                    sender.sendMessage("Sent command:" + args[1]);
                    List response = cli.sendCommand(args[1]);
                    for (Object element : response) {
                        sender.sendMessage("Received message:" + element);
                    }
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong..");
                }
                return true;
            }

            if (args.length == 1 && args[0].equals("disconnect")) {
                Telnet cli = playerCli.get(sender.getName());
                if (cli == null) {
                    sender.sendMessage(ChatColor.RED + "There's no existing client.");
                    return true;
                }

                try {
                    boolean succeed = cli.Disconnect();
                    String traceback = (succeed) ? "Disconnected." : ChatColor.RED + "Already disconnected from the host, or didn't connected at all...";
                    sender.sendMessage(traceback);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Something went wrong..");
                }
                return true;
            }

            if (args.length == 1 && args[0].equals("delete")) {
                if (playerCli.get(sender.getName()) == null) {
                    sender.sendMessage(ChatColor.RED + "There's no existing client.");
                    return true;
                }
                playerCli.remove(sender.getName());
                return true;
            }
        }
        return false;
    }
}
