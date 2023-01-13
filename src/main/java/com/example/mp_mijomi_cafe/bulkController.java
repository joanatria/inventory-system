package com.example.mp_mijomi_cafe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class bulkController {
    @FXML
    private TextField fileName;
    private Stage popUpWindow;
    private Ingredient ingredient;
    public boolean okButtonIsClicked = false;

    public void setPopUpWindow(Stage popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
    }

    public void okButtonClicked() throws FileNotFoundException {
        String file = "/Users/joanatria/Desktop/mp_mijomi_cafe/src/main/java/com/example/mp_mijomi_cafe/bulk.csv";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        try{
            while ((s = br.readLine()) != null) {
                String[] split = s.split(",");
                ingredient.setSKU(split[0]);
                ingredient.setItem(split[1]);
                ingredient.setCategory(split[2]);
                ingredient.setBrand(split[3]);
                ingredient.setItemSize(Integer.parseInt(split[4]));
                ingredient.setUnit(split[5]);
                ingredient.setColor(split[6]);
                ingredient.setType(split[7]);
                ingredient.setDescription(split[8]);
            }
        } catch(IOException e){e.printStackTrace();}

        okButtonIsClicked = true;
        popUpWindow.close();
    }

    public void cancelButtonClicked(){
        popUpWindow.close();
    }
}
