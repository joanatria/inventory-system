package com.example.mp_mijomi_cafe;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

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
    private TableColumn<Ingredient, String> brandColumn;
    @FXML
    private TableColumn<Ingredient, Integer> itemSizeColumn;
    @FXML
    private TableColumn<Ingredient, String> unitColumn;
    @FXML
    private TableColumn<Ingredient, String> colorColumn;
    @FXML
    private TableColumn<Ingredient, String> typeColumn;
    @FXML
    private TableColumn<Ingredient, String> descriptionColumn;
    public boolean addButtonIsClicked = false;

    private Main main;
    ObservableList<Ingredient> listIngredient;
    ObservableList<String> row;

    public void setMain(Main main) {
        this.main = main;
        ingredientTable.setItems(main.getIngredientData());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public static String generateSKU(Ingredient ingredient) {

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
        char last = trcategory.charAt(trcategory.length() - 1);
        char fitem = tritem.charAt(0);
        char litem = tritem.charAt(tritem.length() - 1);
        StringBuilder sku = new StringBuilder();
        sku.append(first);
        sku.append(last);
        sku.append(fitem);
        sku.append(litem);
        sku.append("-");

        int randomNum = (int) Math.floor(Math.random() * (9999 - 0000 + 1) + 0000);
        String randNum = String.valueOf(randomNum);
        if (randNum.length() == 4) {
            sku.append(randNum);
        } else if (randNum.length() < 4 && randNum.length() > -1) {
            int len = 4 - randNum.length();
            for (int i = 0; i < len; i++) {
                sku.append(0);
            }
            sku.append(randNum);
        }

        String skucode = (String.valueOf(sku)).toUpperCase();
        return skucode;
    }

    public void addButtonClicked() throws IOException {
        addButtonIsClicked = true;
        Ingredient newIngredient = new Ingredient();
        boolean okButtonIsClicked = main.showAddNewWindow(newIngredient);
        row = selectItemSQL(newIngredient.getItem());

        if (okButtonIsClicked) {
            main.getIngredientData().add(newIngredient);
            if (row.isEmpty()) {
                addToSQL(newIngredient);
                updateTable();
            }
            else{
                updateSQL(newIngredient);
                updateTable();
            }
        }
    }

    public void updateButtonClicked() throws IOException {
        Ingredient selectedIngredient = ingredientTable.getSelectionModel().getSelectedItem();
        if (selectedIngredient != null) {
            boolean okButtonIsClicked = main.showUpdateExistingWindow(selectedIngredient);
            if (okButtonIsClicked) {
                updateSQL(selectedIngredient);
                updateTable();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an ingredient to update!");

            alert.show();
        }
    }

    public void restockButtonClicked() throws IOException {
        Ingredient ingredient = new Ingredient();

        boolean okButtonIsClicked = main.showRestockWindow(ingredient);
        if (okButtonIsClicked) {
            updateSQL(ingredient);
            updateTable();
        }
    }

    public void itemUsageButtonClicked() throws IOException {
        Ingredient ingredient = new Ingredient();
        boolean okButtonIsClicked = main.showItemUsageWindow(ingredient);
        if (okButtonIsClicked) {
            updateSQL(ingredient);
            updateTable();
        }
    }

    public void deleteButtonClicked() throws IOException {
        try{
            int indexToDelete = ingredientTable.getSelectionModel().getSelectedIndex();
            deleteFromSQL(ingredientTable.getSelectionModel().getSelectedItem());
            ingredientTable.getItems().remove(indexToDelete);
            updateTable();
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an ingredient to delete.");
            alert.show();
        }
    }

    public void importButtonClicked(){
        try{
            Ingredient ingredient = new Ingredient();
            boolean okButtonIsClicked = main.showBulkImportWindow(ingredient);
            ObservableList<String> eachItem;
            eachItem = bulkController.returnQueries();
            if (okButtonIsClicked) {
                eachItem = bulkController.returnQueries();
                for (int i = 0; i < eachItem.size()-1; ++i ){
                    updateSQLL(eachItem.get(i));
                    updateTable();
                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fix your csv file format!");

            alert.show();
        }
    }

    public static void updateSQLL(String SQL_UPDATE) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.createStatement();
            statement.executeUpdate(SQL_UPDATE);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please fix your csv file format!");

                alert.show();
            }
        }
    }

    public static ObservableList<Ingredient> loadIngredients() {
        Connection connection = null;
        PreparedStatement statement = null;

        ObservableList<Ingredient> list = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.prepareStatement("select * from Ingredient");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(new Ingredient(rs.getString("SKU"), rs.getString("Item"), rs.getString("Category"), rs.getString("Brand"), rs.getInt("Amount"), rs.getString("Unit"), rs.getString("Color"), rs.getString("Type"), rs.getString("Description")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static void addToSQL(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement statement = null;

        final String SQL_INSERT = "INSERT INTO Ingredient (SKU, Item, Category, Brand, Amount, Unit, Color, Type, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");

            statement = connection.prepareStatement(SQL_INSERT);

            statement.setString(1, ingredient.getSKU());
            statement.setString(2, ingredient.getItem());
            statement.setString(3, ingredient.getCategory());
            statement.setString(4, ingredient.getBrand());
            statement.setString(5, String.valueOf(ingredient.getItemSize()));
            statement.setString(6, ingredient.getUnit());
            statement.setString(7, ingredient.getColor());
            statement.setString(8, ingredient.getType());
            statement.setString(9, ingredient.getDescription());

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

    public static ObservableList<String> selectSQL(String SKUValue) {
        Connection connection = null;
        PreparedStatement statement = null;

        ObservableList<String> list = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.prepareStatement("select * from Ingredient WHERE SKU='" + SKUValue + "'");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("SKU"));
                list.add(rs.getString("Item"));
                list.add(rs.getString("Category"));
                list.add(rs.getString("Brand"));
                list.add(Integer.toString(rs.getInt("Amount")));
                list.add(rs.getString("Unit"));
                list.add(rs.getString("Color"));
                list.add(rs.getString("Type"));
                list.add(rs.getString("Description"));

            }

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
        return list;
    }

    public static ObservableList<String> selectItemSQL(String itemValue) {
        Connection connection = null;
        PreparedStatement statement = null;

        ObservableList<String> list = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = connection.prepareStatement("select * from Ingredient WHERE Item='" + itemValue + "'");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("SKU"));
                list.add(rs.getString("Item"));
                list.add(rs.getString("Category"));
                list.add(rs.getString("Brand"));
                list.add(Integer.toString(rs.getInt("Amount")));
                list.add(rs.getString("Unit"));
                list.add(rs.getString("Color"));
                list.add(rs.getString("Type"));
                list.add(rs.getString("Description"));

            }

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
        return list;
    }

    public static void updateSQL(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");

            String value1 = ingredient.getSKU();
            String value2 = ingredient.getItem();
            String value3 = ingredient.getCategory();
            String value4 = ingredient.getBrand();
            String value5 = String.valueOf(ingredient.getItemSize());
            String value6 = ingredient.getUnit();
            String value7 = ingredient.getColor();
            String value8 = ingredient.getType();
            String value9 = ingredient.getDescription();

            final String SQL_UPDATE = "UPDATE Ingredient set SKU='" + value1 + "', Item='" + value2 + "', Category='" + value3 + "', Brand='" + value4 + "', Amount='" + value5 + "', Unit='" + value6 + "', Color='" + value7 + "', Type='" + value8 + "', Description='" + value9 + "' WHERE SKU='" + value1 + "'";

            statement = connection.prepareStatement(SQL_UPDATE);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Error!");

                alert.show();
            }
        }
    }

    public static void deleteFromSQL(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement statement = null;

        final String SQL_DELETE = "DELETE FROM Ingredient WHERE SKU = ?";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");

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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Error!");

                alert.show();
            }
        }
    }

    public void updateTable() {
        SKUColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        itemColumn.setCellValueFactory(cellData -> cellData.getValue().itemProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        itemSizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asObject());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        listIngredient = loadIngredients();
        ingredientTable.setItems(listIngredient);
    }

    public void openPDF(){
        main.pdf();
    }
}

