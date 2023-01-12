package com.example.mp_mijomi_cafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class IngredientController implements Initializable {
    @FXML
    private TableView<Ingredient> ingredientTable;

    @FXML
    private TableColumn<Ingredient, String> SKUColumn;

    @FXML
    private TableColumn<Ingredient, String> itemColumn;

    @FXML
    private TableColumn<Ingredient, String> categoryColumn;
    @FXML
    private TableColumn<Ingredient, String> itemSizeColumn;

    @FXML
    private TableColumn<Ingredient, String> brandColumn;

    @FXML
    private TableColumn<Ingredient, String> colorColumn;

    @FXML
    private TableColumn<Ingredient, String> typeColumn;

    @FXML
    private TableColumn<Ingredient, String> descriptionColumn;

    private Main main;
    private Stage popUpWindow;
    ObservableList<Ingredient> ingredientList;

    public void setMain(Main main){
        this.main = main;
        ingredientTable.setItems(main.getIngredientData());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public void addButtonClicked() throws IOException, SQLException {
        Ingredient newIngredient = new Ingredient();
        boolean okButtonClicked = main.showPopUpWindow(newIngredient);

        if (okButtonClicked) {
            main.getIngredientData().add(newIngredient);
            addToSQL(newIngredient);
            updateTable();
        }
    }

    public void updateButtonClicked() throws IOException, SQLException {
        Ingredient selectedIngredient = ingredientTable.getSelectionModel().getSelectedItem();
        if (selectedIngredient != null){
            boolean okButtonClicked = main.showPopUpWindow(selectedIngredient);
            if (okButtonClicked){
                updateSQL(selectedIngredient);
                updateTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an ingredient to update!");

            alert.show();
        }
    }

    public void deleteButtonClicked() throws IOException {
        int indexToDelete = ingredientTable.getSelectionModel().getSelectedIndex();
        deleteFromSQL(ingredientTable.getSelectionModel().getSelectedItem());
        ingredientTable.getItems().remove(indexToDelete);
        updateTable();
    }

    public static ObservableList<Ingredient> loadIngredients(){
        Connection connection = null;
        PreparedStatement statement = null;

        ObservableList<Ingredient> list = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.prepareStatement("select * from Ingredient");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                list.add(new Ingredient(rs.getString("SKU"), rs.getString("Item"), rs.getString("Category"), rs.getInt("Size"), rs.getString("Unit"), rs.getString("Brand"), rs.getString("Color"), rs.getString("Type"), rs.getString("Description")));
            }
        } catch (Exception e){

        }
        return list;
    }

    public static void addToSQL(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement statement = null;

        final String SQL_INSERT = "INSERT INTO Ingredient (SKU, Item, Category, Size, Brand, Color, Type, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db") ;

            statement = connection.prepareStatement(SQL_INSERT);

            statement.setString(1, ingredient.getSKU());
            statement.setString(2, ingredient.getItem());
            statement.setString(3, ingredient.getCategory());
            statement.setString(4, String.valueOf(ingredient.getItemSize()));
            statement.setString(5, ingredient.getBrand());
            statement.setString(6, ingredient.getColor());
            statement.setString(7, ingredient.getType());
            statement.setString(8, ingredient.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static ObservableList<String> selectSQL(String SKUValue){
        Connection connection = null;
        PreparedStatement statement = null;

        ObservableList<String> list = FXCollections.observableArrayList();

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.prepareStatement("select * from Ingredient");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                list.add(rs.getString("SKU"));
                list.add(rs.getString("Item"));
                list.add(rs.getString("Category"));
                list.add(rs.getString("Size"));
                list.add(rs.getString("Brand"));
                list.add(rs.getString("Color"));
                list.add(rs.getString("Type"));
                list.add(rs.getString("Description"));
            }

        } catch (Exception e){

        }
        return list;
    }

    public static void updateSQL(Ingredient ingredient){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");

            String value1 = ingredient.getSKU();
            String value2 = ingredient.getItem();
            String value3 = ingredient.getCategory();
            int value4 = ingredient.getItemSize();
            String value5 = ingredient.getUnit();
            String value6 = ingredient.getBrand();
            String value7 = ingredient.getColor();
            String value8 = ingredient.getType();
            String value9 = ingredient.getDescription();


            final String SQL_UPDATE = "UPDATE Ingredient set SKU='" + value1 + "',Item= '" + value2 + "',Category= '" +
                    value3 + "',Size= '" + value4 + "',Unit = '" + value5 + "',Brand= '" + value6 + "',Color= '" + value7 + "',Type= '" + value8 + "',Description= '" + value9 + "' where SKU='" + value1 + "' ";

            statement = connection.prepareStatement(SQL_UPDATE);
            statement.executeUpdate();
        } catch (SQLException e){
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public static void deleteFromSQL(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement statement = null;

        final String SQL_DELETE = "DELETE FROM Ingredient WHERE SKU = ?";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db") ;

            statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, ingredient.getSKU());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public void updateTable() {
        SKUColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("SKU"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Item"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Category"));
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("itemSize"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Brand"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Color"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("Description"));

        ingredientList = loadIngredients();
        ingredientTable.setItems(ingredientList);
    }

    public static String generateSKU(Ingredient ingredient){

        String item = ingredient.getItem();
        String category = ingredient.getCategory();
        item = item.replaceAll("\\s", "");

        char itemArr[] = item.toCharArray();
        String tritem = "";

        for (int i = 0; i < itemArr.length; i++) {
            if (itemArr[i] != ' ') {
                tritem += itemArr[i];
            }
        }

        char categArr[] = category.toCharArray();
        String trcategory = "";

        for (int i = 0; i < categArr.length; i++) {
            if (categArr[i] != ' ') {
                trcategory += categArr[i];
            }
        }

        char first = trcategory.charAt(0);
        char last = trcategory.charAt(category.length()-1);
        char fitem = tritem.charAt(0);
        char litem = tritem.charAt(item.length()-1);
        StringBuilder sku = new StringBuilder();
        sku.append(first);
        sku.append(last);
        sku.append(fitem);
        sku.append(litem);
        sku.append("-");

        int randomNum = (int)Math.floor(Math.random() * (9999 - 0000 + 1) + 0000);
        String randNum = String.valueOf(randomNum);
        if (randNum.length() == 4){
            sku.append(randNum);
        }else if (randNum.length() < 4 && randNum.length() >-1){
            int len = 4 - randNum.length();
            for(int i = 0; i < len; i++){
                sku.append(0);
            }
            sku.append(randNum);
        }

        String skucode = (String.valueOf(sku)).toUpperCase();
        System.out.println(skucode);
        return skucode;
    }
}
