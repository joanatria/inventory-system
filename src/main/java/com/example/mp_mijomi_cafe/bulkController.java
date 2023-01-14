package com.example.mp_mijomi_cafe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void okButtonClicked() throws FileNotFoundException {
        file = fileName.getText();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;

        Ingredient ingredient = new Ingredient();

        try{
            while ((s = br.readLine()) != null) {
                String[] split = s.split(",");
                item = split[0];
                System.out.println(Arrays.toString(split));
                category = split[1];
                brand = split[2];
                size = Integer.parseInt(split[3]);
                unit = split[4];

                if (split[5].equals("-")) {
                    color = "";
                } else {
                    color = split[5];
                }

                if (split[6].equals("-")) {
                    type = "";
                } else {
                    type = split[6];
                }

                if (split[7].equals("-")) {
                    description = "";
                } else {
                    description = split[7];
                }

                ingredient.setItem(item);
                ingredient.setCategory(category);
                ingredient.setBrand(brand);
                ingredient.setItemSize(size);
                ingredient.setUnit(unit);
                ingredient.setColor(color);
                ingredient.setType(type);
                ingredient.setDescription(description);
                ingredient.setSKU(IngredientController.generateSKU(ingredient));

                IngredientController.addToSQL(ingredient);
            }
        } catch(IOException e){e.printStackTrace();}
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Amount should be an integer.");
            alert.show();
        };

        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }
}
