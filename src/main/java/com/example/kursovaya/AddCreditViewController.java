package com.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCreditViewController {
    @FXML
    private TextArea creditDescription;

    @FXML
    private TextField creditMaturity;

    @FXML
    private TextField creditMaxSum;

    @FXML
    private TextField creditMinSum;

    @FXML
    private TextField creditName;

    @FXML
    private TextField creditPercent;

    @FXML
    private ComboBox creditType;

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> creditTypes = FXCollections.observableArrayList();

        ResultSet resultSet = DataBaseConnection.statement.executeQuery(
                "SELECT * FROM credit_types"
        );

        while (resultSet.next()){
            creditTypes.add(resultSet.getString("type_name"));
        }
        creditType.setItems(creditTypes);
    }

    public void addCreditButtonClick(MouseEvent mouseEvent) throws SQLException {
        String creditNameText = creditName.getText();
        String creditPercentText = creditPercent.getText();
        String creditMaxSumText = creditMaxSum.getText();
        String creditMinSumText = creditMinSum.getText();
        String creditDescriptionText = creditDescription.getText();
        String creditMaturityText = creditMaturity.getText();
        Integer creditTypeIndex = creditType.getSelectionModel().getSelectedIndex() + 1;

        DataBaseConnection.statement.execute(String.format(
                "INSERT INTO credits values(DEFAULT, '%s', '%s', '%s', '%s', '%s', '%s', '%d')",
                creditNameText,
                creditPercentText,
                creditMaxSumText,
                creditMinSumText,
                creditDescriptionText,
                creditMaturityText,
                creditTypeIndex
        ));
    }
}
