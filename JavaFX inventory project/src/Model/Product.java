package Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
public class Product {
    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int stock;
    private int min;
    private int max;
    private double price;
    private  int id;
    private String name;

    public Product(int id, String name,double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

   

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public  void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public  void addAssociatedPart(Part Part) {
        associatedParts.add(Part);
    }

    public  void deleteAssociatedPart(Part partDelete) {
        associatedParts.remove(partDelete);
    }

}
