package com.example.kursovaya;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserViewController {
    @FXML
    private Label userFio;

    @FXML
    private Label userId;

    @FXML
    private Label userLogin;

    @FXML
    private Label userRole;

    @FXML
    public void initialize(){
        userId.setText(UserInfo.userID);
        userFio.setText(UserInfo.userFIO);
        userLogin.setText(UserInfo.userLogin);
        userRole.setText(UserInfo.userRole);
    }
}
