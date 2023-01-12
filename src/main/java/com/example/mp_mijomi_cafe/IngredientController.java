package com.example.mp_mijomi_cafe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IngredientController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}