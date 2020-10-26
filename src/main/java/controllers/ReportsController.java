package controllers;

import com.jfoenix.controls.JFXComboBox;
import data.Data;
import definitions.Role;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class ReportsController {
    @FXML JFXComboBox<String> reportBox;
    @FXML ListView<User> userList;
    ArrayList<User> result = new ArrayList<>();

    @FXML
    private void initialize() {
        reportBox.setItems(FXCollections.observableArrayList(
                "List of users that follows everyone who follow him",
                "List of users who post at least one twit from register"
        ));
        if(Data.user.getRole() == Role.MANAGER) {
            reportBox.getItems().addAll(
                    "List of Users suspected of cheating",
                    "List of users who post 10 comment under more than 3 twit (each one)"
            );
        }
    }

    @FXML
    private void getReports() {
        String report = reportBox.getValue();
        switch (report) {
            case "List of users that follows everyone who follow him":
                getUserFollowBack();
                break;
            case "List of users who post at least one twit from register":
                getUserPostEveryDay();
                break;
            case "List of Users suspected of cheating":
                getUserCheating();
                break;
            case "List of users who post 10 comment under more than 3 twit (each one)":
                getUser10Post();
                break;
        }
        PageController.setUserListView(result, userList);
    }

    private void getUser10Post() {
        System.out.println(4);
    }

    private void getUserCheating() {
        System.out.println(3);
        result = DatabaseController.getUserCheating();
    }

    private void getUserPostEveryDay() {
        System.out.println(2);
//        result = DatabaseController.getUserPostEveryDay();
//        PageController.setUserListView(result, userList);
    }

    private void getUserFollowBack() {
        System.out.println(1);
        result = DatabaseController.getFollowingFollowBack();
        PageController.setUserListView(result, userList);
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }
}
