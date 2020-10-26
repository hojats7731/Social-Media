package controllers;

import com.jfoenix.controls.JFXButton;
import data.Data;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import definitions.Role;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class SectionsController {
    @FXML JFXButton reportsBtn;
    @FXML FontAwesomeIconView reportIcon;
    @FXML Pane pane;

    @FXML
    private void initialize() {
        if(Data.user.getRole() == Role.USER) {
            pane.getChildren().remove(reportsBtn);
            pane.getChildren().remove(reportIcon);
        }
    }

    @FXML
    private void onDashboardBtnPressed() {
        PageController.changeScene("Dashboard");
    }

    @FXML
    private void onNewTwitBtnPressed() {
        PageController.changeScene("NewTwit");
    }

    @FXML
    private void onSearchBtnPressed() {
        PageController.changeScene("Search");
    }

    @FXML
    private void onTrendsBtnPressed() {
        PageController.changeScene("Trends");
    }

    @FXML
    private void onReportsBtnPressed() {
        PageController.changeScene("Reports");
    }
}
