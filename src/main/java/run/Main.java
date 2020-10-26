package run;

import data.Data;
import definitions.Role;
import definitions.Twit;
import definitions.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    private static void setStage(Stage stage) {
        Main.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        primaryStage.setTitle("Tweeter");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
