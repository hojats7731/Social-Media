package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import data.Data;
import definitions.Twit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NewTwitController {
    @FXML private StackPane stackPane;
    @FXML private Label username;
    @FXML private ImageView userImage;
    @FXML private JFXButton sectionsBtn, sendBtn;
    @FXML private JFXTextArea text;

    @FXML
    private void initialize() {
        username.setText(Data.user.getFirstName());
        userImage.setImage(Data.user.getImage());
        PageController.clipImage(userImage);
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }

    @SuppressWarnings("Duplicates")
    @FXML
    private void onSendBtnPressed() {
        String string = text.getText();
        if(!string.contains("#") || (string.indexOf("#") == string.lastIndexOf("#"))) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Hashtag Missing!"));
            content.setBody(new Text("Text should have at least 2 hashtag!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("OK");
            button.setOnAction(event -> dialog.close());
            content.setActions(button);
            dialog.show();
        } else {
            Twit twit = new Twit(Data.user, string);
            DatabaseController.addTwit(twit);
            Data.user.getShowTwits().add(0, twit);

            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("TwitSent!"));
            content.setBody(new Text("Twit successfully has been sent!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("OK");
            button.setOnAction(event -> PageController.changeScene("Sections"));
            content.setActions(button);
            dialog.show();
        }
    }
}
