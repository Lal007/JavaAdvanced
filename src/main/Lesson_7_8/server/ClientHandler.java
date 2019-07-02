package main.Lesson_7_8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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

                            //Авторизация
                            if (str.startsWith("/auth ")){
                                String[] tokens = str.split(" ");
                                for (String s:tokens) {
                                    System.out.print(s + " ");
                                }
                                System.out.println();
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                System.out.println(newNick);
                                if (newNick != null){
                                    if (!server.isNickExist(newNick)){
                                        System.out.println("/authok " + newNick);
                                        sendMsg("/authok " + newNick);
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

                            //Регистрация
                            if (str.startsWith("/register ")){
                                String[] tokens = str.split(" ", 4);
                                if (AuthService.registerNewUser(tokens[1], tokens[2], tokens[3])){
                                    sendMsg("Регистрация успешна");
                                }else sendMsg("Ошибка регистрации");
                            }

                        }
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")){
                                if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");
                                    break;
                                }
                                else if (str.startsWith("/w")){
                                    String[] tokens = str.split(" ", 3);
                                    if (tokens.length != 3) continue;
                                    server.broadCastMsg(tokens[2], tokens[1], nick);
                                }
                                else if (str.startsWith("/blackList ")){                    //Добавление в черный список
                                    String banNick = str.split(" ")[1];
                                    int id = AuthService.getIdByNick(nick);
                                    if (!(banNick == null) && AuthService.getIdByNick(banNick) != -1){
                                        AuthService.addUserToBlacklist(id, banNick);
                                        sendMsg("Ник " + banNick + " успешно добавлен в черный список");
                                    }else {
                                        sendMsg("Ник не найден");
                                    }
                                }
                            }else {
                                server.broadCastMsg(ClientHandler.this, nick + ": " + str);
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
    public boolean blockedNextUser(String senderNick){
        int id = AuthService.getIdByNick(this.nick);
        ArrayList<String> bannedUsers = AuthService.getBannedUsers(id);
        if (bannedUsers != null){
            return bannedUsers.contains(senderNick);
        }else {
            return false;
        }
    }

}
