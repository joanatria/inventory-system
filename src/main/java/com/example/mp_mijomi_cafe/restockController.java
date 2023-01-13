package com.example.mp_mijomi_cafe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class restockController {
    @FXML
    private TextField SKUField;
    @FXML
    private TextField addAmountField;
    @FXML
    private Label itemLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label brandLabel;
    @FXML
    private Label itemSizeLabel;
    @FXML
    private Label unitLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label descriptionLabel;
    private Stage popUpWindow;
    private Ingredient ingredient;
    public boolean okButtonIsClicked = false;
    ObservableList<String> row;
    private int amountAdded;
    private int origAmount;
    private int newAmount;
    private String SKU;

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
    }

    public void okButtonClicked(){
        amountAdded = Integer.parseInt(addAmountField.getText());
        origAmount = Integer.parseInt(itemSizeLabel.getText());
        newAmount = amountAdded + origAmount;

        ingredient.setSKU(SKUField.getText());
        ingredient.setItem(itemLabel.getText());
        ingredient.setCategory(categoryLabel.getText());
        ingredient.setBrand(brandLabel.getText());
        ingredient.setItemSize(newAmount);
        ingredient.setUnit(unitLabel.getText());
        ingredient.setColor(colorLabel.getText());
        ingredient.setType(typeLabel.getText());
        ingredient.setDescription(descriptionLabel.getText());

        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }

    public void searchButtonClicked(){
        Ingredient ingredient = new Ingredient();
        ingredient.setSKU(SKUField.getText());

        row = IngredientController.selectSQL(SKUField.getText());

        String value1 = String.valueOf(row.get(1));
        String value2 = String.valueOf(row.get(2));
        String value3 = String.valueOf(row.get(3));
        String value4 = String.valueOf(row.get(4));
        String value5 = String.valueOf(row.get(5));
        String value6 = String.valueOf(row.get(6));
        String value7 = String.valueOf(row.get(7));
        String value8 = String.valueOf(row.get(8));

        itemLabel.setText(value1);
        categoryLabel.setText(value2);
        brandLabel.setText(value3);
        itemSizeLabel.setText(value4);
        unitLabel.setText(value5);
        colorLabel.setText(value6);
        typeLabel.setText(value7);
        descriptionLabel.setText(value8);
    }
}
