package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import definitions.Twit;
import definitions.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class UserController extends ListCell<User> {
    @FXML private Label name, username;
    @FXML private ImageView image;
    @FXML private AnchorPane aPane;
    @FXML private Pane pane;
    @FXML private JFXButton followBtn;
    @FXML private FontAwesomeIconView followIcon;
    private User user;

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);

        if(empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader mLLoader = new FXMLLoader(getClass().getResource("/fxml/User.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setInfo(user);
            setText(null);
            setGraphic(getPane());
        }
    }

    private void setInfo(User user) {
        this.user = user;
        name.setText(user.getFirstName() + " " + user.getLastName());
        username.setText(user.getUsername());
        image.setImage(user.getImage());
        PageController.clipImage(image);
        setFollowButton();
    }

    private void setFollowButton() {
        switch (user.getFollowStatus()) {
            case "followerFollowing":
                followBtn.setStyle("-fx-background-color:  #C8C442");
                followBtn.setText("Following");
                followIcon.setGlyphName("CHECK_CIRCLE");
                break;
            case "following":
                followBtn.setStyle("-fx-background-color:  #C8C442");
                followBtn.setText("Following");
                followIcon.setGlyphName("CHECK_CIRCLE");
                break;
            case "none":
                followBtn.setStyle("-fx-background-color:  #dcdbc4");
                followBtn.setText("Follow");
                followIcon.setGlyphName("PLUS_CIRCLE");
                break;
            case "follower":
                followBtn.setStyle("-fx-background-color:  #dadc9c");
                followBtn.setText("Follow Back");
                followIcon.setGlyphName("PLUS_CIRCLE");
                break;
        }
    }

    @FXML
    private void onFollowButton() {
        switch (user.getFollowStatus()) {
            case "followerFollowing":
                followBtn.setStyle("-fx-background-color:  #C8C442");
                followBtn.setText("Following");
                followIcon.setGlyphName("CHECK_CIRCLE");
                DatabaseController.addFollowing(user);
                break;
            case "following":
                followBtn.setStyle("-fx-background-color:  #C8C442");
                followBtn.setText("Following");
                followIcon.setGlyphName("CHECK_CIRCLE");
                DatabaseController.addFollowing(user);
                break;
            case "none":
                followBtn.setStyle("-fx-background-color:  #dcdbc4");
                followBtn.setText("Follow");
                followIcon.setGlyphName("PLUS_CIRCLE");
                DatabaseController.removeFollowing(user);
                break;
            case "follower":
                followBtn.setStyle("-fx-background-color:  #dadc9c");
                followBtn.setText("Follow Back");
                followIcon.setGlyphName("PLUS_CIRCLE");
                DatabaseController.removeFollowing(user);
                break;
        }
    }

    @FXML
    private void followUser() {
        String status = user.getFollowStatus();
        switch (status) {
            case "followerFollowing":
                status = "follower";
                break;
            case "follower":
                status = "followerFollowing";
                break;
            case "following":
                status = "none";
                break;
            case "none":
                status = "following";
                break;
        }
        System.out.println(status);
        user.setFollowStatus(status);
        onFollowButton();
    }

    private AnchorPane getPane() {
        return aPane;
    }

    @FXML
    private void goToProfile() {
        PageController.goToProfile(user);
    }
}
