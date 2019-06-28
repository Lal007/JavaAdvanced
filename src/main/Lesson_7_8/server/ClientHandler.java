package main.Lesson_7_8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer server;
    private String nick;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/auth ")){
                                String[] tokens = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                System.out.println(newNick);
                                if (newNick != null){
                                    if (!server.isNickExist(newNick)){
                                        sendMsg("/authok");
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    }else {
                                        sendMsg("Этот пользователь уже авторизован!");
                                    }
                                }else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                break;
                            }
                            else if (str.startsWith("/w")){
                                String[] tokens = str.split(" ", 3);
                                if (tokens.length != 3) continue;
                                server.broadCastMsg(tokens[2], tokens[1], nick);
                            }else {
                                server.broadCastMsg(nick + ": " + str);
                            }
                        }
                    } catch (IOException e) {
                        try {
                            socket.close();
                            server.unsubscribe(ClientHandler.this);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } finally {
                        try {
                            socket.close();
                            server.unsubscribe(ClientHandler.this);
                            System.out.println("Отключение клиента");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getNick(){
        return nick;
    }

}
