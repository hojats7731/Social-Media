package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.Data;
import definitions.PrivateQuestions;
import definitions.Role;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class ForgetPasswordController {

    @FXML
    JFXComboBox<String> PQBox;
    @FXML
    JFXTextField answer;
    @FXML
    JFXTextField username;

    @FXML
    private void initialize() {
        PQBox.setItems(FXCollections.observableArrayList(
                PrivateQuestions.Q1.toString(),
                PrivateQuestions.Q2.toString(),
                PrivateQuestions.Q3.toString()
        ));
    }

    @FXML
    private void onCntBtnPressed() {
        String uname = username.getText();
        int pqNum = getIndex(PQBox.getValue());
        String ans = answer.getText();
        boolean state = DatabaseController.checkQuestion(uname, pqNum, ans);
        if(state)
            PageController.changeScene("Dashboard");
    }

    private int getIndex(String value) {
        for (int i = 0; i < PrivateQuestions.values().length; i++)
            if(PrivateQuestions.values()[i].toString().equals(value))
                return i;
        return -1;
    }
}
