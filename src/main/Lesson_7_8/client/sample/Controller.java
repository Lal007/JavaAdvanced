package main.Lesson_7_8.client.sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Controller{
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1, authorization, registration;


    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    VBox VBoxChat;

    @FXML
    ScrollPane scrollChat;

    public boolean isAuthorized() {
        return isAuthorized;
    }

    private boolean isAuthorized = false;
    private String nick = "default";

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;

        if (!isAuthorized){
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        }else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }


    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/authok")){
                                nick = str.split(" ", 2)[1];
                                setAuthorized(true);
                                System.out.println("authorization ok");
                                break;
                            }else{
                                //textArea.appendText(str + "\n");
                                receiveMsg("Server: " + str);
                            }
                        }
                        //textArea.clear();
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/serverClosed")){
                                break;
                            }
                            //textArea.appendText(str + "\n");
                            receiveMsg(str);
                        }
                    } catch (EOFException  | SocketException e) {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } catch (IOException e) {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(){
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void receiveMsg(String msg){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //scrollChat.setFitToHeight(true);
                scrollChat.setFitToWidth(true);
                scrollChat.setVvalue(1.0);

                Label label = new Label(msg + "  \n");
                VBox vbox = new VBox();

                if (msg.startsWith(nick)){
                    vbox.setAlignment(Pos.TOP_RIGHT);
                }else{
                    vbox.setAlignment(Pos.TOP_LEFT);
                }
                vbox.getChildren().add(label);
                VBoxChat.getChildren().add(vbox);
            }
        });


    }

    @FXML
    public void exitApplication() {
        System.out.println("Exit!!!");
        try {
            if (out != null) {
                out.writeUTF("/end");
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Нет связи с сервером.");
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void tryToAuth(String login, String pass){
        if (socket == null || socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth " + login + " " + pass);
        }catch (IOException | NullPointerException e) {
            System.out.println("Сервер недоступен");
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
