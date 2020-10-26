package controllers;

import com.jfoenix.controls.JFXTextField;
import data.Data;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InfoRegPageController {
    @FXML ImageView imageView;
    @FXML JFXTextField firstName, lastName, bio;

    @FXML
    private void initialize() {
        PageController.clipImage(imageView);
    }

    @FXML
    public void changeImage() {
        final FileChooser fileChooser = new FileChooser();
        File imageFile = new File(fileChooser.showOpenDialog(imageView.getScene().getWindow()).getAbsolutePath());
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        initialize();
    }

    @FXML
    private void onCntBtnPressed() {
        Data.user.setFirstName(firstName.getText());
        Data.user.setLastName(lastName.getText());
        Data.user.setBiography(bio.getText());
        Data.user.setImage(imageView.getImage());
        PageController.changeScene("PrivateQuestionPage");
    }
}
