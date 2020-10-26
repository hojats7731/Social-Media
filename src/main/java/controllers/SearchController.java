package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.Data;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import definitions.Twit;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import run.Main;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController {
    @FXML FontAwesomeIconView userIcon;
    @FXML JFXTextField searchField;
    @FXML JFXButton searchBtn, sectionsBtn;
    @FXML ListView<User> userList;


    @FXML
    private void search() {
        String search = searchField.getText();
        ArrayList<User> result = DatabaseController.searchUser(search);
        PageController.setUserListView(result, userList);
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }

    @FXML
    private void changeSearch() {
        PageController.changeScene("HashtagSearch");
    }
}
