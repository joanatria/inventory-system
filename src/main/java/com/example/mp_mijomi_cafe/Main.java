package com.example.mp_mijomi_cafe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     *
     * Loads the start-view.fxml file
     */
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

    /**
     *
     * @param ingredient
     * @return
     * @throws IOException
     *
     * Loads the addNew.fxml file as a popUpWindow
     */
    public boolean showPopUpWindow(Ingredient ingredient) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addNew.fxml"));
        Parent root = loader.load();

        Stage popUpWindow = new Stage();
        Scene scene = new Scene(root);

        EditIngredientController editIngredientControl = loader.getController();
        editIngredientControl.setPopUpWindow(popUpWindow);
        editIngredientControl.setIngredient(ingredient);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
        popUpWindow.setResizable(false);
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));

        return editIngredientControl.okButtonIsClicked;
    }

    public static void main(String[] args) {
        launch(args);
    }
}