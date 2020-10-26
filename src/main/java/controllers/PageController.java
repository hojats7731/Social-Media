package controllers;

import data.Data;
import definitions.Twit;
import definitions.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import run.Main;

import java.io.IOException;
import java.util.ArrayList;

class PageController {
    static void changeScene(String scene) {
        if(scene.equals("Dashboard"))
            Data.depth = 0;
        try {
            FXMLLoader loader = new FXMLLoader(PageController.class.getResource("/fxml/" + scene + ".fxml"));
            Parent root = loader.load();
            Main.getStage().setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void clipImage(ImageView imageView) {
        Circle clip = new Circle(imageView.getX() + imageView.getFitWidth()/2, imageView.getY() + imageView.getFitHeight()/2, imageView.getFitHeight()/2);
        imageView.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);

        imageView.setClip(null);
        imageView.setEffect(new DropShadow(5, Color.BLACK));
        imageView.setImage(image);
    }

    static void goToTwitPage(Twit twit) {
        try {
            FXMLLoader loader = new FXMLLoader(PageController.class.getResource("/fxml/TwitPage.fxml"));
            Parent root = loader.load();
            Main.getStage().setScene(new Scene(root));
            TwitPageController controller = loader.getController();
            controller.initialize(twit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void goToProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(PageController.class.getResource("/fxml/Profile.fxml"));
            Parent root = loader.load();
            Main.getStage().setScene(new Scene(root));
            DatabaseController.getUserData(user);
            DatabaseController.getUserTwits(user);
            ProfileController controller = loader.getController();
            controller.initialize(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void setUserListView(ArrayList<User> users, ListView<User> userList) {
        ObservableList<User> observableList = FXCollections.observableArrayList();
        observableList.addAll(users);
        userList.getItems().remove(0, userList.getItems().size());
        userList.setItems(observableList);
        userList.setCellFactory(listView -> new UserController());
    }
}
