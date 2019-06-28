package main.Lesson_7_8.server;


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
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client){
        clients.remove(client);
        broadcastClientList();
    }

    public void broadCastMsg(ClientHandler from, String msg){
        for (ClientHandler client:clients) {
            if (!client.blockedNextUser(from.getNick())){
                client.sendMsg(msg);
            }
        }
    }

    public void broadCastMsg(String msg, String nickTo, String nickFrom){
        for (ClientHandler client:clients) {
            if (client.getNick().equals(nickTo) && client.blockedNextUser(nickTo)){
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

    public void broadcastClientList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clientList");
        for (ClientHandler client: clients) {
            sb.append(" " + client.getNick());
        }
        String out = sb.toString();

        for (ClientHandler client: clients) {
            client.sendMsg(out);
        }
    }
}
