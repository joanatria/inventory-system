package com.example.mp_mijomi_cafe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditIngredientController {
    @FXML
    private TextField SKUField;
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
    ObservableList<String> row;

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;

        //SKUField.setText(ingredient.getSKU());
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


        ingredient.setItem(itemField.getText());
        ingredient.setCategory(categoryField.getText());
        ingredient.setBrand(brandField.getText());
        ingredient.setItemSize(Integer.parseInt(itemSizeField.getText()));
        ingredient.setUnit(unitField.getText());
        ingredient.setColor(colorField.getText());
        ingredient.setType(typeField.getText());
        ingredient.setDescription(descriptionField.getText());
        ingredient.setSKU(IngredientController.generateSKU(ingredient));

        //ingredient.setSKU("TEST");
        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }

    public void SKUButtonClicked(){
        Ingredient ingredient = new Ingredient();
        ingredient.setSKU(SKUField.getText());
        System.out.println(ingredient.getSKU());

        row = IngredientController.selectSQL(SKUField.getText());

        System.out.println(row);
        String value1 = String.valueOf(row.get(1));
        String value2 = String.valueOf(row.get(2));
        String value3 = String.valueOf(row.get(3));
        String value4 = String.valueOf(row.get(4));
        String value5 = String.valueOf(row.get(5));
        String value6 = String.valueOf(row.get(6));
        String value7 = String.valueOf(row.get(7));
        String value8 = String.valueOf(row.get(8));

        itemField.setText(value1);
        categoryField.setText(value2);
        brandField.setText(value3);
        itemSizeField.setText(value4);
        unitField.setText(value5);
        colorField.setText(value6);
        typeField.setText(value7);
        descriptionField.setText(value8);
    }
}
