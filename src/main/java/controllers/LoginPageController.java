package controllers;

import com.jfoenix.controls.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class LoginPageController {
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private StackPane pane;

    @FXML
    private void initialize() {
        username.setText("hojats7");
        password.setText("123");
    }

    @SuppressWarnings("Duplicates")
    @FXML
    public void onLoginBtnPressed() {
        String username = this.username.getText();
        String password = this.password.getText();

        if (DatabaseController.isUserCorrect(username, password))
            PageController.changeScene("Sections");
        else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Error!"));
            content.setBody(new Text("Username or Password is incorrect!"));
            JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("OK");
            button.setOnAction(event -> dialog.close());
            content.setActions(button);
            dialog.show();
        }
    }

    @FXML
    public void onRegisterBtnPressed() {
        PageController.changeScene("ForgetPassword");
    }
}
