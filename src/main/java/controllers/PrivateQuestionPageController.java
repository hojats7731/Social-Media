package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.Data;
import definitions.PrivateQuestions;
import definitions.Role;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class PrivateQuestionPageController {
    @FXML JFXComboBox<String> roleBox;
    @FXML JFXComboBox<String> PQBox;
    @FXML JFXTextField answer;

    @FXML
    private void initialize() {
        PQBox.setItems(FXCollections.observableArrayList(
                PrivateQuestions.Q1.toString(),
                PrivateQuestions.Q2.toString(),
                PrivateQuestions.Q3.toString()
        ));
        roleBox.setItems(FXCollections.observableArrayList("User", "Manager", "Analyser"));
    }

    @FXML
    private void onCntBtnPressed() {
        int pqNum = getIndex(PQBox.getValue());
        Data.user.setPersonalQuestionNumber(pqNum);
        Data.user.setPersonalQuestionAnswer(answer.getText());
        String role = roleBox.getValue();
        if(role == null || role.equals("User"))
            Data.user.setRole(Role.USER);
        else if (role.equals("Manager"))
            Data.user.setRole(Role.MANAGER);
        else if (role.equals("Analyser"))
            Data.user.setRole(Role.ANALYSER);
        DatabaseController.addUser(Data.user);
        PageController.changeScene("Dashboard");
    }

    private int getIndex(String value) {
        for (int i = 0; i < PrivateQuestions.values().length; i++)
            if(PrivateQuestions.values()[i].toString().equals(value))
                return i;
        return -1;
    }
}
