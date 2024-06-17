package org.itt.utility;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static List<ServerThread> clients = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ServerThread serverThread = new ServerThread(socket, clients);
                clients.add(serverThread);
                executorService.execute(serverThread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void notifyClients(String message) {
        for (ServerThread client : clients) {
            client.sendMessage(message);
        }
    }
}
