package controllers;

import data.Data;

import definitions.Twit;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import run.Main;

import java.io.IOException;

public class DashboardController {
    @FXML ImageView userImage;
    @FXML Label username;
    @FXML ListView<Twit> twitList;
    private User user = Data.user;

    @FXML
    public void initialize() {
        username.setText(Data.user.getFirstName());
        userImage.setImage(Data.user.getImage());
        PageController.clipImage(userImage);
        setListView();
    }

    private void setListView() {
        ObservableList<Twit> observableList = FXCollections.observableArrayList();
        DatabaseController.getUserShowTwits(user);
        observableList.addAll(user.getShowTwits());
        twitList.setItems(observableList);
        twitList.setCellFactory(listView -> new TwitController());
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }
}
