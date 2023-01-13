package com.example.mp_mijomi_cafe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class updateExistingController {
    @FXML
    private Label itemLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private TextField itemSizeField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    private Stage popUpWindow;
    private Ingredient ingredient;
    public boolean okButtonIsClicked = false;
    ObservableList<String> row;
    private String SKU;
    private String item;
    private String category;

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;

        SKU = ingredient.getSKU();
        item = ingredient.getItem();
        category = ingredient.getCategory();
        itemLabel.setText(item);
        categoryLabel.setText(category);
        brandField.setText(ingredient.getBrand());
        if(Character.isDigit((char) ingredient.getItemSize())){
            itemSizeField.setText(Double.toString(ingredient.getItemSize()));
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect input. Numerical values only.");
            alert.show();
        }
        unitField.setText(ingredient.getUnit());
        colorField.setText(ingredient.getColor());
        typeField.setText(ingredient.getType());
        descriptionField.setText(ingredient.getDescription());
    }

    public void okButtonClicked(){
        ingredient.setSKU(SKU);
        ingredient.setItem(item);
        ingredient.setCategory(category);
        ingredient.setBrand(brandField.getText());
        ingredient.setItemSize(Integer.parseInt(itemSizeField.getText()));
        ingredient.setUnit(unitField.getText());
        ingredient.setColor(colorField.getText());
        ingredient.setType(typeField.getText());
        ingredient.setDescription(descriptionField.getText());

        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }
}
