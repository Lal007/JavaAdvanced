package main.Lesson_4.sample1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller1 {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    public void sendMsg(){
        textArea.appendText(textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }
}
