package main.Lesson_7_8.client.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

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
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);
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
    }


    public static void main(String[] args) {
        //--module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
        launch(args);
    }
}
