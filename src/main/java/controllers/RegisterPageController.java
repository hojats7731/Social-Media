package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.Data;
import definitions.User;
import javafx.fxml.FXML;

public class RegisterPageController {
    @FXML private JFXTextField username, email, phoneNumber;
    @FXML private JFXPasswordField password;

    @FXML
    public void onCntBtnPressed() {
        Data.user = new User(username.getText(), password.getText(), email.getText(), phoneNumber.getText());
        PageController.changeScene("InfoRegPage");
    }
}
