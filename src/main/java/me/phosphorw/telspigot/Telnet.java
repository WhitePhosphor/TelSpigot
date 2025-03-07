package me.phosphorw.telspigot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a telnet client.
 */

public class Telnet {
    private Socket socket;
    private InputStream IStream;
    private OutputStream OStream;

    public void Connect(String hostAddr, int port) throws IOException {
        socket = new Socket(hostAddr, port);
        IStream = socket.getInputStream();
        OStream = socket.getOutputStream();
    }

    public List sendCommand(String command) throws IOException {
        OStream.write((command + "\n").getBytes());
        OStream.flush();

        byte[] byteBuffer = new byte[1024];
        int bytesRead;
        List<String> response = new ArrayList<>();
        while ((bytesRead = IStream.read(byteBuffer)) != -1) {
            response.add(new String(byteBuffer, 0, bytesRead));
        }
        return response;
    }

    public boolean Disconnect() throws IOException {
        if (socket == null) {
            return false;
        }

        if (!socket.isClosed()) {
            socket.close();
            return true;
        } else {
            return false;
        }
    }
}
