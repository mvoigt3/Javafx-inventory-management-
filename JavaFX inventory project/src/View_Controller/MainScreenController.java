package View_Controller;

import Model.Inhouse;
import Model.Product;
import View_Controller.AddPartController;
import View_Controller.AddProductController;
import Model.Part;
import Model.inventory;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainScreenController implements Initializable {

    public ObservableList<Part> partInventory = FXCollections.observableArrayList();
    public ObservableList<Product> productInventory = FXCollections.observableArrayList();

    public ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    public ObservableList<Product> productsInventorySearch = FXCollections.observableArrayList();

    private Object FXMLloader;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Part, Integer> displayProductId;

    @FXML
    private TableColumn<Product, String> displayProductName;

    @FXML
    private TableColumn<Product, Integer> displayProductInv;

    @FXML
    private TableColumn<Product, Integer> displayProductPrice;

    @FXML
    public TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> displayPartID;

    @FXML
    private TableColumn<Part, String> displayPartName;

    @FXML
    private TableColumn<Part, Integer> displayInvPart;

    @FXML
    private TableColumn<Part, Double> displayPricePart;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button searchPartButton;
    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button searchProductButton;

    @FXML
    private TextField searchProductField;

    @FXML
    public TextField searchPartField;

    @FXML
    private Button exitMainButton;
    @FXML
    private MouseEvent event;

    @FXML
    private void addPart(MouseEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/addPart.fxml"));
            View_Controller.AddPartController controller = new View_Controller.AddPartController();

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }

    @FXML
    private void addProduct(MouseEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/addProduct.fxml"));
            View_Controller.AddProductController controller = new View_Controller.AddProductController();

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }


    @FXML
    private void deletePart(MouseEvent event) {
        Alert alertConfirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alertConfirmDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part partToDelete = partsTable.getSelectionModel().getSelectedItem();
 
            Model.inventory.deletePart(partToDelete);
            partInventory.remove(partToDelete);
        }

    }

    @FXML
    void deleteProduct(MouseEvent event) {
        Alert alertConfirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Product?");
        Optional<ButtonType> result = alertConfirmDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product productToDelete = productsTable.getSelectionModel().getSelectedItem();
            Model.inventory.deleteProduct(productToDelete);
            productInventory.remove(productToDelete);
        }
    }

    @FXML
    void clearText(MouseEvent event
    ) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (searchPartField == field) {
            if (partInventory.size() != 0) {
                partsTable.setItems(partInventory);
            }
        }
        if (searchProductField == field) {
            if (productInventory.size() != 0) {
                productsTable.setItems(productInventory);
            }
        }
    }

    @FXML
    void exitMain(MouseEvent event) {
        Platform.exit();
    }


    @FXML
    void modifyPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/modifyPart.fxml"));
        loader.load();

        ModifyPartController MDPController = loader.getController();

        MDPController.sendModifyPart(partsTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void modifyProduct(ActionEvent event) throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/modifyProduct.fxml"));
        loader.load();

        ModifyProductController MDCController = loader.getController();

        MDCController.sendModifyProduct(productsTable.getSelectionModel().getSelectedItem());
   
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    public void searchProduct(MouseEvent event) {

        if (!searchProductField.getText().trim().isEmpty()) {
            productsInventorySearch.clear();
            Model.inventory.getAllProducts().stream().filter((p) -> (p.getName().contains(searchProductField.getText().trim()))).forEachOrdered((p) -> {
                productsInventorySearch.add(p);
            });
            productsTable.setItems(productsInventorySearch);
            productsTable.refresh();
        }
    }


    @FXML
    public void searchPart(MouseEvent event) {

        if (!searchPartField.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            Model.inventory.getAllParts().stream().filter((p) -> (p.getName().contains(searchPartField.getText().trim()))).forEachOrdered((p) -> {
                partsInventorySearch.add(p);
            });

            partsTable.setItems(partsInventorySearch);
            partsTable.refresh();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partInventory.setAll(Model.inventory.getAllParts());
        partsTable.setItems(inventory.getAllParts());
        productInventory.setAll(Model.inventory.getAllProducts());
        productsTable.setItems(inventory.getAllProducts());
        displayPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        displayPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        displayInvPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        displayPricePart.setCellValueFactory(new PropertyValueFactory<>("price"));
        displayProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        displayProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        displayProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        displayProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
