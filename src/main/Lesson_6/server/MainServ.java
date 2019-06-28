package main.Lesson_6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServ {
    private Vector<ClientHandler1> clients;

    public MainServ() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Клиент подключен");
                new ClientHandler1(socket, this);
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

    public void subscribe(ClientHandler1 client){
        clients.add(client);
    }

    public void unsubscribe(ClientHandler1 client){
        clients.remove(client);
    }

    public void broadCastMsg(String msg){
        for (ClientHandler1 client:clients) {
            client.sendMsg(msg);
        }

        /*Iterator<ClientHandler1> i = clients.iterator();

        while (i.hasNext()) {
            ClientHandler1 client = i.next();
            if (!(client.isClientClosed())) {
                client.sendMsg(msg);
            } else {
                i.remove();
                System.out.println("Клиент отключился");

            }
        }*/
    }
}
