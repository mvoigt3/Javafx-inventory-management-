package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
//Parts
    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);

        }
    }

    public static void deletePart(Part selectedPart) {
        getAllParts().remove(selectedPart);
    }

    public static Part lookUpPart(int partId) {
        return null;
    }

    public static ObservableList<Part> lookUpPart(String partName) {
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static boolean updatedPart(int idPart, Part parts) {
        int index = 0;
        for (Part part : inventory.getAllParts()) {
            index++;
            if (part.getId() == idPart) {
                inventory.getAllParts().set(index, part);
                return true;
            }
        }
        return false;
    }
// Products 
    public static void addProduct(Product newProduct) {
        if (newProduct != null) ;
        allProducts.add(newProduct);
    }

    public static void deleteProduct(Product selectedProduct) {
        getAllProducts().remove(selectedProduct);
    }

    public static Product lookUpProduct(int productId) {
        return null;
    }

    public static ObservableList<Product> lookUpProduct(String productName) {
        return null;
    }

    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);

    }

    public static boolean updatedProduct(int idProduct, Product products) {
        int index = 0;
        for (Product product : inventory.getAllProducts()) {
            index++;
            if (product.getId() == idProduct) {
                inventory.getAllProducts().set(index, products);
                return true;
            }
        }
        return false;
    }
//getters for the observable lists;
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

}
