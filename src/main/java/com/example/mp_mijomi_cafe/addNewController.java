package com.example.mp_mijomi_cafe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addNewController {
    @FXML
    private TextField itemField;
    @FXML
    private TextField categoryField;
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

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;

        itemField.setText(ingredient.getItem());
        categoryField.setText(ingredient.getCategory());
        brandField.setText(ingredient.getBrand());
        itemSizeField.setText(Integer.toString(ingredient.getItemSize()));
        unitField.setText(ingredient.getUnit());
        colorField.setText(ingredient.getColor());
        typeField.setText(ingredient.getType());
        descriptionField.setText(ingredient.getDescription());
    }

    public void okButtonClicked(){
        if(itemField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in the required fields (*).");
            alert.show();
        } else if (categoryField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in the required fields (*).");
            alert.show();
        } else if (brandField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in the required fields (*).");
            alert.show();
        }else if (itemSizeField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in the required fields (*).");
            alert.show();
        }else if (unitField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in the required fields (*).");
            alert.show();
        }else if ((Integer.parseInt(itemSizeField.getText())) < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect input. No negative inputs allowed.");
            alert.show();
        }else if(Character.isLetter((char) ingredient.getItemSize())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect input. Numerical values only.");
            alert.show();
        }else{
            ingredient.setItem(itemField.getText());
            ingredient.setCategory(categoryField.getText());
            ingredient.setBrand(brandField.getText());
            ingredient.setItemSize(Integer.parseInt(itemSizeField.getText()));
            ingredient.setUnit(unitField.getText());
            ingredient.setColor(colorField.getText());
            ingredient.setType(typeField.getText());
            ingredient.setDescription(descriptionField.getText());
            ingredient.setSKU(IngredientController.generateSKU(ingredient));
            okButtonIsClicked = true;
            popUpWindow.close();
        }


        ingredient.setItem(itemField.getText());
        ingredient.setCategory(categoryField.getText());
        ingredient.setBrand(brandField.getText());
        ingredient.setItemSize(Integer.parseInt(itemSizeField.getText()));
        ingredient.setUnit(unitField.getText());
        ingredient.setColor(colorField.getText());
        ingredient.setType(typeField.getText());
        ingredient.setDescription(descriptionField.getText());
        ingredient.setSKU(IngredientController.generateSKU(ingredient));

        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }
}
