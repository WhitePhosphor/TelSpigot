package me.phosphorw.telspigot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.bukkit.command.CommandSender;

public class Telnet {
    private Socket Cli;
    private InputStream IStream;
    private OutputStream OStream;

    public void Connect(String hostAddr, int port, CommandSender sender) throws IOException {
        Cli = new Socket(hostAddr, port);
        sender.sendMessage("Successfully connected to " + hostAddr + " on port" + port + ".");

        IStream = Cli.getInputStream();
        OStream = Cli.getOutputStream();
        sender.sendMessage("Successfully set up IOStream.");
    }

    public void sendCommand(String command, CommandSender sender) throws IOException {
        OStream.write((command + "\n").getBytes());
        OStream.flush();
        sender.sendMessage("Sent command:" + command);

        byte[] byteBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = IStream.read(byteBuffer)) != -1) {
            String response = new String(byteBuffer, 0, bytesRead);
            sender.sendMessage("Response from server:" + response);
        }
    }

    public void Disconnect(CommandSender sender) throws IOException {
        if (Cli != null && !Cli.isClosed()) {
            String addr = String.valueOf(Cli.getInetAddress());
            Cli.close();
            sender.sendMessage("Disconnected from server: " + addr);
        }
    }
}
