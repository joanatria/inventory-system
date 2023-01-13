package com.example.mp_mijomi_cafe;

import javafx.beans.property.*;

public class Ingredient {
    private final StringProperty SKU;
    private final StringProperty item;
    private final StringProperty category;
    private final StringProperty brand;
    private final IntegerProperty itemSize;
    private final StringProperty unit;
    private final StringProperty color;
    private final StringProperty type;
    private final StringProperty description;


    public Ingredient(){
        this(null, null, null, null, 0, null, null, null, null);
    }

    public Ingredient(String SKU, String item, String category, String brand, int size, String unit, String color, String type, String description) {
        this.SKU = new SimpleStringProperty(SKU);
        this.item = new SimpleStringProperty(item);
        this.category = new SimpleStringProperty(category);
        this.brand = new SimpleStringProperty(brand);
        this.itemSize = new SimpleIntegerProperty(size);
        this.unit = new SimpleStringProperty(unit);
        this.color = new SimpleStringProperty(color);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
    }

    public void setSKU (String SKU){
        this.SKU.set(SKU);
    }
    public String getSKU (){
        return SKU.get();
    }
    public StringProperty SKUProperty() {
        return SKU;
    }

    public void setItem (String item){
        this.item.set(item);
    }
    public String getItem (){
        return item.get();
    }
    public StringProperty itemProperty() {
        return item;
    }

    public void setCategory (String category){
        this.category.set(category);
    }
    public String getCategory (){
        return category.get();
    }
    public StringProperty categoryProperty() {
        return category;
    }

    public void setBrand (String brand){
        this.brand.set(brand);
    }
    public String getBrand (){
        return brand.get();
    }
    public StringProperty brandProperty() {
        return brand;
    }

    public void setItemSize (int itemSize){
        this.itemSize.set(itemSize);
    }
    public int getItemSize (){
        return itemSize.get();
    }
    public IntegerProperty sizeProperty() {
        return itemSize;
    }

    public void setUnit (String unit){
        this.unit.set(unit);
    }
    public String getUnit (){
        return unit.get();
    }
    public StringProperty unitProperty() {
        return unit;
    }


    public void setColor (String color){
        this.color.set(color);
    }
    public String getColor (){
        return color.get();
    }
    public StringProperty colorProperty() {
        return color;
    }

    public void setType (String type){
        this.type.set(type);
    }
    public String getType (){
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }

    public void setDescription (String description){
        this.description.set(description);
    }
    public String getDescription (){
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }
}