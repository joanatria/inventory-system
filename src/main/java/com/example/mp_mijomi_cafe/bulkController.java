package com.example.mp_mijomi_cafe;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class bulkController {
    @FXML
    private TextField fileName;
    private Stage popUpWindow;
    public boolean okButtonIsClicked = false;
    private String file;
    private String item;
    private String category;
    private String brand;
    private int size;
    private String unit;
    private String color;
    private String type;
    private String description;
    private ObservableList<String> row;
    private static ObservableList<String> updateQueries = FXCollections.observableArrayList();

    ObservableList<Ingredient> list = FXCollections.observableArrayList();

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void okButtonClicked() throws FileNotFoundException {
        try{
            file = fileName.getText();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;

            Ingredient ingredient = new Ingredient();
            while ((s = br.readLine()) != null) {
                String[] split = s.split(",");
                item = split[0];
                item.replaceAll("'", "");

                category = split[1];
                category.replaceAll("'", "");

                brand = split[2];
                brand.replaceAll("'", "");

                size = Integer.parseInt(split[3]);

                unit = split[4];
                unit.replaceAll("'", "");

                row = IngredientController.selectItemSQL(item);

                if (split[5].equals("-")) {
                    color = "";
                } else {
                    color = split[5];
                    color.replaceAll("'", "");
                }

                if (split[6].equals("-")) {
                    type = "";
                } else {
                    type = split[6];
                    type.replaceAll("'", "");
                }

                if (split[7].equals("-")) {
                    description = "";
                } else {
                    description = split[7];
                    description.replaceAll("'", "");
                }

                ingredient.setItem(item);
                ingredient.setCategory(category);
                ingredient.setBrand(brand);
                ingredient.setItemSize(size);
                ingredient.setUnit(unit);
                ingredient.setColor(color);
                ingredient.setType(type);
                ingredient.setDescription(description);

                if (row.isEmpty()) {
                    ingredient.setSKU(IngredientController.generateSKU(ingredient));
                    IngredientController.addToSQL(ingredient);
                }
                else{
                    String SKU = String.valueOf(row.get(0));
                    ingredient.setSKU(SKU);
                    String updateQuery = setUpdateQueries(ingredient);
                    //System.out.println(setUpdateQueries(ingredient));

                    updateQueries.add(updateQuery);
                }
            }
        }
        catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect filename format.");
            alert.show();
        }catch(IOException e){e.printStackTrace();}
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Amount should be an integer.");
            alert.show();
        }

        okButtonIsClicked = true;
        popUpWindow.close();
    }
    
    public String setUpdateQueries(Ingredient ingredient){
        String value1 = ingredient.getSKU();
        value1.replaceAll("'", "");
        String value2 = ingredient.getItem();
        value2.replaceAll("'", "");
        String value3 = ingredient.getCategory();
        value3.replaceAll("'", "");
        String value4 = ingredient.getBrand();
        value4.replaceAll("'", "");
        String value5 = String.valueOf(ingredient.getItemSize());
        value5.replaceAll("'", "");
        String value6 = ingredient.getUnit();
        value6.replaceAll("'", "");
        String value7 = ingredient.getColor();
        value7.replaceAll("'", "");
        String value8 = ingredient.getType();
        value8.replaceAll("'", "");
        String value9 = ingredient.getDescription();
        value9.replaceAll("'", "");

        String SQL_UPDATE = "UPDATE Ingredient set SKU='" + value1 + "', Item='" + value2 + "', Category='" + value3 + "', Brand='" + value4 + "', Amount='" + value5 + "', Unit='" + value6 + "', Color='" + value7 + "', Type='" + value8 + "', Description='" + value9 + "' WHERE SKU='" + value1 + "'";

        return SQL_UPDATE;
    }

    public static ObservableList<String> returnQueries(){
        return updateQueries;
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }
}
