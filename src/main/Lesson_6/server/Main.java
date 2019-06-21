package main.Lesson_6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class Main {
    private Vector<ClientHandler> clients;

    public Main() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Клиент подключен");
                clients.add(new ClientHandler(socket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void broadCastMsg(String msg){
        /*for (ClientHandler client:clients) {
            if (!client.isClientClosed()){
                client.sendMsg(msg);
            }else clients.remove(client);
        }*/

        Iterator<ClientHandler> i = clients.iterator();

        while (i.hasNext()) {
            ClientHandler client = i.next();
            if (!(client.isClientClosed())) {
                client.sendMsg(msg);
            } else {
                i.remove();
                System.out.println("Клиент отключился");

            }
        }
    }
}
