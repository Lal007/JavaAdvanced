package main.Lesson_7.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServer {
    private Vector<ClientHandler> clients;

    public MainServer() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Клиент подключен");
                new ClientHandler(socket, this);
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
            AuthService.disconnect();
        }

    }

    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client){
        clients.remove(client);
    }

    public void broadCastMsg(String msg){
        for (ClientHandler client:clients) {
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

    public void broadCastMsg(String msg, String nickTo, String nickFrom){
        for (ClientHandler client:clients) {
            if (client.getNick().equals(nickTo)){
                client.sendMsg(nickFrom + " - private message: " + msg);
            }else if (client.getNick().equals(nickFrom)){
                client.sendMsg("private message to " + nickTo + ": " +  msg);
            }
        }
    }

    public boolean isNickExist(String nick){
        for (ClientHandler client:clients) {
            if (client.getNick().equals(nick)) return true;
        }
        return false;
    }
}
