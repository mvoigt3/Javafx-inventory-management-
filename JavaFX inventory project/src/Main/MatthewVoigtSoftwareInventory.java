package Main;

import Model.Inhouse;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import Model.inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//Author Matthew Voigt WGU

public class MatthewVoigtSoftwareInventory extends Application {

    public static void main(String[] args) {
        Part D = new Outsourced(1, "Part 15", 3.24, 4, 3, 101, "Jahnson and Johnson");
        Part a1 = new Inhouse(2, "A1", 2.00, 2, 1, 100, 89690);
        Part a2 = new Inhouse(3, "Part A2", 4.00, 50, 1, 200, 63011);
        Part b = new Inhouse(4, "Part B", 3.99, 9, 5, 100, 102);
        Part C = new Outsourced(5, "Part C", 3.14, 9, 1, 100, "Johnson and Johnson");
        Model.inventory.addPart(D);
        Model.inventory.addPart(C);
        Model.inventory.addPart(a1);
        Model.inventory.addPart(b);
        Model.inventory.addPart(a2);
        Model.inventory.addPart(new Inhouse(6, "Part A3", 5.99, 15, 5, 100, 104));
        Model.inventory.addPart(new Inhouse(7, "Part A4", 6.99, 9, 5, 100, 105));
        Product prod1 = new Product(5, "Super Product", 13.5, 3, 1, 15);
        prod1.addAssociatedPart(a2);
        Model.inventory.addProduct(prod1);

        Product prod2 = new Product(1, "fine Product", 12.8, 4, 10, 100);
        prod2.addAssociatedPart(a2);
        Model.inventory.addProduct(prod2);
        Product prod3 = new Product(2, "okay Product", 300.2, 30, 5, 100);
        prod3.addAssociatedPart(a2);
        Model.inventory.addProduct(prod3);
        Product prod4 = new Product(3, "bad Product", 42.8, 20, 1, 100);
        Model.inventory.addProduct(prod4);
        prod4.addAssociatedPart(a2);
        Product prod5 = new Product(4, "awfulProduct", 5000.02, 40, 10, 100);
        Model.inventory.addProduct(prod5);
        prod5.addAssociatedPart(a2);

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
