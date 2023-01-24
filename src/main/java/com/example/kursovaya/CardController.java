package com.example.kursovaya;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class CardController {
    @FXML
    private Label creditDescription;

    @FXML
    private Rectangle creditIconBackground;

    @FXML
    private Label creditMaxSum;

    @FXML
    private Label creditMinSum;

    @FXML
    private Label creditName;

    @FXML
    private Label creditPercent;

    private Credit credit;

    public void setData(Credit credit){
        this.credit = credit;
        creditName.setText(credit.getName());
        creditPercent.setText(credit.getPercent().toString());
        creditMaxSum.setText("" + credit.getMaxsum());
        creditMinSum.setText("" + credit.getMinsum());
        creditDescription.setText(credit.getDescription());
        creditIconBackground.setFill(credit.getGradient());
    }
}
