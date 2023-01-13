package com.example.mp_mijomi_cafe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private ObservableList<Ingredient> ingredientData = FXCollections.observableArrayList();

    public ObservableList<Ingredient> getIngredientData(){
        return ingredientData;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 710, 500);
//        stage.setTitle("Machine Problem : MiJoMi Inventory");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.getIcons().add(new Image (Main.class.getResourceAsStream("icon.png")));

        IngredientController ingredientControl = loader.getController();
        ingredientControl.setMain(this);
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
        popUpWindow.getIcons().add(new Image (Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();

        return editIngredientControl.okButtonClicked;
    }

    public static void main(String[] args) {
        launch();
    }
}