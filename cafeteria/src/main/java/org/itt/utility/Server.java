package org.itt.utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final List<ObjectOutputStream> clientOutputStreams = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                addClientOutputStream(outputStream);

                ServerThread serverThread = new ServerThread(socket, outputStream);
                executorService.execute(serverThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public static void notifyClients(String message) {
        for (ObjectOutputStream outputStream : clientOutputStreams) {
            try {
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addClientOutputStream(ObjectOutputStream outputStream) {
        clientOutputStreams.add(outputStream);
    }

    public static void removeClientOutputStream(ObjectOutputStream outputStream) {
        clientOutputStreams.remove(outputStream);
    }
}
