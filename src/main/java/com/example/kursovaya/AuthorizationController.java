package com.example.kursovaya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.*;


public class AuthorizationController {
    @FXML
    TextField userLogin, userPassword;

    @FXML
    protected void authorizationButtonClick() {
        try {
            ResultSet resultSet = DataBaseConnection.statement.executeQuery(
                    String.format(
                            "SELECT user_id, user_login, user_fullname, role_description from users, user_roles WHERE user_login = '%s' AND user_password = '%s' " +
                                    "AND users.user_role = user_roles.role_id",
                            userLogin.getText(),
                            userPassword.getText()
                    )
            );

            if(!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка.");
                alert.setHeaderText("Неверный логин или пароль!");
                alert.show();
            }else{
                UserInfo.userID = resultSet.getString("user_id");
                UserInfo.userLogin = resultSet.getString("user_login");
                UserInfo.userFIO = resultSet.getString("user_fullname");
                UserInfo.userRole = resultSet.getString("role_description");

                Stage stage = (Stage) userLogin.getScene().getWindow();
                stage.close();

                stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(AuthorizationController.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Кредитный отдел");
                stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}