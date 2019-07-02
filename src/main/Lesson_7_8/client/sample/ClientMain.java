package main.Lesson_7_8.client.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 350, 275));
        primaryStage.setOnHidden(e-> controller.exitApplication());
        primaryStage.show();
        controller.authorization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label secondLabel = new Label();
                HBox textBox = new HBox();
                VBox mainBox = new VBox();

                TextField login = new TextField();
                login.setPromptText("Логин");
                PasswordField pass = new PasswordField();
                pass.setPromptText("Пароль");
                Button signIn = new Button("Sign in");
                textBox.setAlignment(Pos.TOP_CENTER);
                textBox.getChildren().add(login);
                textBox.getChildren().add(pass);

                mainBox.setAlignment(Pos.BASELINE_CENTER);
                mainBox.getChildren().add(textBox);
                mainBox.getChildren().add(signIn);

                secondLabel.setGraphic(mainBox);

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 230, 100);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Авторизация");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 50);
                newWindow.setY(primaryStage.getY() + 50);

                newWindow.show();
                signIn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.tryToAuth(login.getText(), pass.getText());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (controller.isAuthorized()){
                            newWindow.close();
                        }else {
                            login.requestFocus();
                        }
                    }
                });
                pass.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.tryToAuth(login.getText(), pass.getText());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (controller.isAuthorized()){
                            newWindow.close();
                        }else {
                            login.requestFocus();
                        }
                    }
                });
            }
        });
        controller.registration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label secondLabel = new Label();
                VBox buttonBox = new VBox();
                VBox mainBox = new VBox();

                TextField nick = new TextField();
                nick.setPromptText("Ник");

                TextField login = new TextField();
                login.setPromptText("Логин");

                PasswordField pass = new PasswordField();
                pass.setPromptText("Пароль");

                Button regButton = new Button("Sign in");
                mainBox.setAlignment(Pos.TOP_CENTER);
                mainBox.getChildren().add(nick);
                mainBox.getChildren().add(login);
                mainBox.getChildren().add(pass);

                buttonBox.setAlignment(Pos.BASELINE_CENTER);
                buttonBox.getChildren().add(regButton);

                mainBox.getChildren().add(buttonBox);
                secondLabel.setGraphic(mainBox);

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 230, 200);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Регистрация");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 50);
                newWindow.setY(primaryStage.getY() + 50);
                newWindow.show();

                regButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.regNewUser(nick.getText(), login.getText(), pass.getText());
                        newWindow.close();
                    }
                });

                pass.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.regNewUser(nick.getText(), login.getText(), pass.getText());
                        newWindow.close();
                    }
                });
            }
        });
        controller.clientList.setPrefWidth(75);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem privateMsg = new MenuItem("Личные сообщения");
        privateMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Label persLabel = new Label();
                VBox box = new VBox();

                TextField pm = new TextField();
                pm.setPromptText("Введите личное сообщение");
                Button sendB = new Button("Отправить");

                box.setAlignment(Pos.TOP_CENTER);
                box.getChildren().addAll(pm, sendB);

                persLabel.setGraphic(box);

                StackPane secondLayout = new StackPane();
                secondLayout.getChildren().add(persLabel);

                Scene secondScene = new Scene(secondLayout, 230, 100);

                Stage newWindow = new Stage();
                newWindow.setTitle("Личные сообщения");
                newWindow.setScene(secondScene);

                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

                newWindow.show();

                sendB.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String nickFrom = controller.getNick();
                        String nickTo = (String) controller.clientList.getSelectionModel().getSelectedItem();

                        if (!nickTo.contains("- Вы")){
                            try {
                                controller.out.writeUTF("/w " + nickTo + " " + pm.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            newWindow.close();
                        }else {
                            controller.receiveMsg("Вы пытаетесь написать себе!");
                            newWindow.close();
                        }
                    }
                });

                pm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String nickFrom = controller.getNick();
                        String nickTo = (String) controller.clientList.getSelectionModel().getSelectedItem();

                        if (!nickTo.contains("- Вы")){
                            try {
                                controller.out.writeUTF("/w " + nickTo + " " + pm.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            newWindow.close();
                        }else {
                            controller.receiveMsg("Вы пытаетесь написать себе!");
                            newWindow.close();
                        }
                    }
                });
            }
        });

        MenuItem toBlackList = new MenuItem("Черный список");
        toBlackList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label blackLabel = new Label();
                VBox vbox = new VBox();
                HBox box = new HBox();
                String nickTo = (String) controller.clientList.getSelectionModel().getSelectedItem();
                TextField tf = new TextField("Вы уверены, что хотите добавить " + nickTo + " в черный список?");
                Text text = new Text();
                text.textProperty().bind(tf.textProperty());
                Button apply = new Button("Подтвердить");
                Button cancel = new Button("Отменить");

                box.setAlignment(Pos.BOTTOM_CENTER);
                vbox.setAlignment(Pos.TOP_CENTER);
                box.getChildren().addAll(apply, cancel);
                vbox.getChildren().add(text);
                vbox.getChildren().add(box);
                blackLabel.setGraphic(vbox);

                StackPane secondLayout = new StackPane();
                secondLayout.getChildren().add(blackLabel);

                Scene secondScene = new Scene(secondLayout, 370, 100);

                Stage newWindow = new Stage();
                newWindow.setTitle("Личные сообщения");
                newWindow.setScene(secondScene);

                newWindow.setX(primaryStage.getX() + 50);
                newWindow.setY(primaryStage.getY() + 50);

                newWindow.show();

                apply.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            controller.out.writeUTF("/blackList " + nickTo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        newWindow.close();
                    }
                });
                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        newWindow.close();
                    }
                });
            }
        });

        contextMenu.getItems().addAll(privateMsg, toBlackList);

        controller.clientList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                //System.out.println(controller.clientList.getSelectionModel().getSelectedItem());
                contextMenu.show(controller.clientList, event.getScreenX(), event.getScreenY());




            }
        });
    }


    public static void main(String[] args) {
        //--module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
        launch(args);
    }
}
