package com.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainViewController {
    @FXML
    ComboBox sortingComboBox;

    @FXML
    private ComboBox filteringComboBox;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchTextField;

    private List<Credit> credits = new ArrayList<>();

    private List<String> sortingColumns = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> sortingItems = FXCollections.observableArrayList(
                "Нет сортировки", "Имя", "Процентная ставка", "Максимальная сумма", "Минимальная сумма", "Тип кредита"
        );
        sortingComboBox.setItems(sortingItems);

        ObservableList<String> filteringItems = FXCollections.observableArrayList();

        ResultSet resultSet = DataBaseConnection.statement.executeQuery(
                "SELECT type_name FROM credit_types"
        );
        while (resultSet.next()){
            filteringItems.add(resultSet.getString("type_name"));
        }
        filteringComboBox.setItems(filteringItems);

        sortingColumns = Arrays.asList("credit_id", "credit_name", "credit_percent", "credit_maxsum", "credit_minsum", "type_name");

        formDataChanged();
    }

    public void openUserInfoClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AuthorizationController.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Кредитный отдел");
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void newCreditClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AuthorizationController.class.getResource("add-credit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Кредитный отдел");
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void redrawGrid(String query) throws SQLException {
        Credit credit;
        grid.getChildren().clear();
        credits.clear();

        ResultSet resultSet = DataBaseConnection.statement.executeQuery(query);

        while (resultSet.next()) {
            Random random = new Random();
            credit = new Credit();

            credit.setDescription(resultSet.getString("credit_description"));
            credit.setMaxsum(resultSet.getInt("credit_maxsum"));
            credit.setMinsum(resultSet.getInt("credit_minsum"));
            credit.setPercent(resultSet.getFloat("credit_percent"));
            credit.setMaturityDate(resultSet.getString("credit_maturity_date"));
            credit.setName(String.format("%s | %s", resultSet.getString("credit_name"), resultSet.getString("type_name")));
            credit.setType(resultSet.getInt("credit_type"));

            Stop[] stops = new Stop[]{
                    new Stop(0, new Color(random.nextDouble(0.0, 1.0), random.nextDouble(0.0, 1.0), random.nextDouble(0.0, 1.0), 1.0)),
                    new Stop(1, new Color(random.nextDouble(0.0, 1.0), random.nextDouble(0.0, 1.0), random.nextDouble(0.0, 1.0), 1.0))};
            LinearGradient gradient = new LinearGradient(1, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);

            credit.setGradient(gradient);
            credits.add(credit);
        }

        try {
            for (int i = 0; i < credits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();
                cardController.setData(credits.get(i));

                grid.add(anchorPane, 0, i);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void formDataChanged() throws SQLException {
        String request = "SELECT credit_id, credit_name, credit_percent, credit_maxsum, credit_minsum, credit_description, " +
                "credit_maturity_date, credit_type, type_name FROM credits, credit_types WHERE credits.credit_type = credit_types.type_id ";

        if(searchTextField.getText().length() > 0){
            request += String.format("AND credit_name LIKE '%%%s%%' ", searchTextField.getText());
        }

        if(filteringComboBox.getSelectionModel().getSelectedIndex() >= 0) {
            request += String.format("AND type_name = '%s'", filteringComboBox.getSelectionModel().getSelectedItem().toString());
        }

        if(sortingComboBox.getSelectionModel().getSelectedIndex() >= 0) {
            request += String.format("ORDER BY %s ", sortingColumns.get(sortingComboBox.getSelectionModel().getSelectedIndex()));
        }

        redrawGrid(request);
    }
}
