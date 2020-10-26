package controllers;

import com.jfoenix.controls.JFXTextArea;
import data.Data;
import definitions.Twit;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.util.Date;

public class ProfileController {
    @FXML ImageView userImage;
    @FXML Label name, email, phone, id, postsNum, followersNum, followingNum;
    @FXML ListView<Twit> twitList;
    @FXML JFXTextArea bio;
    private User user;


    void initialize(User user) {
        this.user = user;
        userImage.setImage(user.getImage());
        PageController.clipImage(userImage);
        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        id.setText(user.getUsername() + "");
        bio.setText(user.getBiography());
        postsNum.setText(user.getTwits().size() + "");
        followersNum.setText(user.getFollowersNum() + "");
        followingNum.setText(user.getFollowingNum() + "");
        postsNum.setText(user.getTwitsNum() + "");
        setListView(user);
    }

    private void setListView(User user) {
        ObservableList<Twit> observableList = FXCollections.observableArrayList();
        observableList.addAll(user.getTwits());
        twitList.setItems(observableList);
        twitList.setCellFactory(listView -> new TwitController());
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }

    @FXML
    private void showFollowing() {
        Data.secondUser = user;
        PageController.changeScene("FollowingPage");
    }

    @FXML
    private void showFollowers() {
        Data.secondUser = user;
        PageController.changeScene("FollowersPage");
    }
}
