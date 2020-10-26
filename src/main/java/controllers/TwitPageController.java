package controllers;

import com.jfoenix.controls.JFXTextArea;
import data.Data;
import definitions.Twit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class TwitPageController {
    @FXML private ListView<Twit> twitList;
    @FXML private JFXTextArea comment;
    private Twit twit;

    void initialize(Twit twit) {
        this.twit = twit;
        ObservableList<Twit> observableList = FXCollections.observableArrayList();
        observableList.add(twit);
        System.out.println(Data.depth);
        if (Data.depth == 1)
            DatabaseController.getComments(twit);
        else
            DatabaseController.getReplies(twit);
        observableList.addAll(twit.getReplies());
        twitList.setItems(observableList);
        twitList.setCellFactory(listView -> new TwitController());
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }

    @FXML
    private void onSendBtnPressed() {
        String text = comment.getText();
        comment.setText("");
        Twit twit = new Twit(Data.user, text);
        twitList.getItems().add(twit);
        this.twit.addReply(twit);
        DatabaseController.addComment(this.twit, twit);
    }
}
