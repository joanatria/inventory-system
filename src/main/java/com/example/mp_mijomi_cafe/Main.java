package com.example.mp_mijomi_cafe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class Main extends Application {


    private ObservableList<Ingredient> ingredientData;

    public Main(){
        ingredientData = IngredientController.loadIngredients();
    }

    public ObservableList<Ingredient> getIngredientData(){
        return ingredientData;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
        popUpWindow.showAndWait();

        return editIngredientControl.okButtonIsClicked;
    }

    public static void main(String[] args) {
        launch(args);
    }
}