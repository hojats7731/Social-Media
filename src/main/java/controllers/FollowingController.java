package controllers;

import com.jfoenix.controls.JFXButton;
import data.Data;
import definitions.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class FollowingController {
    @FXML
    JFXButton sectionsBtn;
    @FXML
    ListView<User> userList;

    @FXML
    private void initialize() {
        ArrayList<User> result = DatabaseController.getFollowing(Data.secondUser);
        PageController.setUserListView(result, userList);
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }
}
