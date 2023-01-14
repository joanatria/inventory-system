package com.example.mp_mijomi_cafe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.File;
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
        stage.setResizable(false);
        stage.show();
        stage.setTitle("Mijomi Inventory");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));


        IngredientController ingredientControl = loader.getController();
        ingredientControl.setMain(this);
    }


    public boolean showAddNewWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addNew.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        addNewController control = loader.getController();
        control.setPopUpWindow(popUpWindow);
        control.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.setTitle("Add Item");
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);


        return control.okButtonIsClicked;
    }

    public boolean showUpdateExistingWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateExisting.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        updateExistingController control = loader.getController();
        control.setPopUpWindow(popUpWindow);
        control.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.setTitle("Update Item");
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);

        return control.okButtonIsClicked;
    }

    public boolean showRestockWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restock.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        restockController control = loader.getController();
        control.setPopUpWindow(popUpWindow);
        control.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.setTitle("Restock Item");
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);


        return control.okButtonIsClicked;
    }

    public boolean showItemUsageWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("itemUsage.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        itemUsageController control = loader.getController();
        control.setPopUpWindow(popUpWindow);
        control.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.setTitle("Item Usage");
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);

        return control.okButtonIsClicked;
    }

    public boolean showBulkImportWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("import.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        bulkController control = loader.getController();
        control.setPopUpWindow(popUpWindow);

        popUpWindow.setScene(scene);
        popUpWindow.setTitle("Import CSV File");
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);


        return control.okButtonIsClicked;
    }
    public void pdf(){
    getHostServices().showDocument(getClass().getResource("User-Manual.pdf").toString());;
    }

    public static void main(String[] args) {
        launch(args);
    }
}