package org.itt.utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream outputStream;

    public ServerThread(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Handle client requests here

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
                Server.removeClientOutputStream(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
