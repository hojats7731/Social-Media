package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.Data;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import definitions.Twit;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import run.Main;

import java.io.IOException;
import java.util.ArrayList;

public class HashtagSearchController {
    @FXML FontAwesomeIconView userIcon;
    @FXML JFXTextField searchField;
    @FXML JFXButton searchBtn, sectionsBtn;
    @FXML ListView<Twit> twitList;


    @FXML
    private void search() {
        String search = searchField.getText();
        ArrayList<Twit> result = DatabaseController.searchHashtag(search);
        setListView(result);
    }

    private void setListView(ArrayList<Twit> twits) {
        ObservableList<Twit> observableList = FXCollections.observableArrayList();
        observableList.addAll(twits);
        twitList.setItems(observableList);
        twitList.setCellFactory(listView -> new TwitController());
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }

    @FXML
    private void changeSearch() {
        PageController.changeScene("Search");
    }
}
