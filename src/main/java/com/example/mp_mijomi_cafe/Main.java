package com.example.mp_mijomi_cafe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private ObservableList<Ingredient> ingredientData = FXCollections.observableArrayList();

    public ObservableList<Ingredient> getIngredientData(){
        return ingredientData;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public boolean showPopUpWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editIngredient.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        EditIngredientController editIngredientControl = loader.getController();
        editIngredientControl.setPopUpWindow(popUpWindow);
        editIngredientControl.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();

        return editIngredientControl.okButtonClicked;
    }

    public static void main(String[] args) {
        launch();
    }
}