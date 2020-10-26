package controllers;

import com.jfoenix.controls.JFXToggleButton;
import definitions.Twit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class TrendsController {
    @FXML ListView<Twit> twitList;
    @FXML JFXToggleButton trends;
    private boolean state = true;
    private ArrayList<Twit> result;
    private ObservableList<Twit> observableList;

    @FXML
    private void initialize() {
        result = DatabaseController.getTrends();
        observableList = FXCollections.observableArrayList();
        observableList.addAll(result);
        twitList.setItems(observableList);
        twitList.setCellFactory(listView -> new TwitController());
    }

    @FXML
    private void toggle() {
        if(state) {
            System.out.println("Time");
            result.sort((a, b) -> (int)(b.getDate().getTime() - a.getDate().getTime()));
            observableList.setAll(result);
            twitList.setItems(observableList);
        } else {
            System.out.println("Trend");
            initialize();
        }
        state = !state;
    }

    @FXML
    private void onSectionBtnPressed() {
        PageController.changeScene("Sections");
    }
}
