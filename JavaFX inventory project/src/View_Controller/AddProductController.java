package View_Controller;

import Model.Inhouse;
import Model.Part;
import Model.inventory;
import java.net.URL;
import java.util.ResourceBundle;
import Model.Product;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AddProductController implements Initializable {

    public ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> currentAssociatedPart = FXCollections.observableArrayList();
    public ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private String exceptionMessage = new String();
    private int productID;

    int productId = 0;
    Product product;
    @FXML
    private Button deleteAssocPart;

    @FXML
    private TextField productStockPrompt;

    @FXML
    private TextField searchPartField;

    @FXML
    private TextField productIdPrompt;

    @FXML
    private Button searchPartsButton;

    @FXML
    private Button addPartButton;

    @FXML
    private Button saveProductButton;

    @FXML
    public TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> assocIdCol;

    @FXML
    private TableColumn<Part, String> assocNameCol;

    @FXML
    private TableColumn<Part, Integer> assocStockCol;

    @FXML
    private TableColumn<Part, Double> assocPriceCol;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> stockPartCol;

    @FXML
    private TableColumn<Part, Double> pricePartCol;

    @FXML
    private Button cancelAddProduct;

    @FXML
    private TextField productNamePrompt;

    @FXML
    private TextField productPricePrompt;

    @FXML
    private TextField productMinPrompt;

    @FXML
    private TextField productMaxPrompt;

    @FXML
    void addAssociatedParrt(MouseEvent event) {
        Part associationPart = partsTable.getSelectionModel().getSelectedItem();
        boolean duplicate = false;

        if (associationPart != null) {
            int id = associationPart.getId();
            for (int i = 0; i < currentAssociatedPart.size(); i++) {
                if (currentAssociatedPart.get(i).getId() == id) {
                    duplicate = true;

                }
            }
            if (duplicate) {
                if (duplicate) {
                    Alert duplicated = new Alert(Alert.AlertType.ERROR);
                    duplicated.setTitle("Error Message");
                    duplicated.setContentText("This part is already associated!");
                    duplicated.showAndWait();
                    return;
                }
            }
            if (!duplicate) 
            {
                currentAssociatedPart.add(associationPart);
            }
        }
        product.addAssociatedPart(associationPart);
    }

    @FXML
    void cancelProduct(MouseEvent event) throws IOException {
        Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alertConfirmCancel.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            inventory.deleteProduct(product);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    void deleteAssociatedPart(MouseEvent event) {
        Alert alertConfirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part for this Product?");
        Optional<ButtonType> result = alertConfirmDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
            currentAssociatedPart.remove(partToRemove);
            product.deleteAssociatedPart(partToRemove);
        }

    }

    @FXML
    void saveProduct(MouseEvent event) throws IOException {

        int idProduct = Integer.parseInt(productIdPrompt.getText());
        String namedProduct = productNamePrompt.getText();
        int stockProduct = Integer.parseInt(productStockPrompt.getText());
        double priceProduct = Double.parseDouble(productPricePrompt.getText());
        int maxProduct = Integer.parseInt(productMaxPrompt.getText());
        int minProduct = Integer.parseInt(productMinPrompt.getText());

        if (minProduct > maxProduct) {
            Alert alertInventoryLimit = new Alert(Alert.AlertType.ERROR);
            alertInventoryLimit.setTitle("Error Message");
            alertInventoryLimit.setContentText("Minimum must be less than maximum!");
            alertInventoryLimit.showAndWait();
            return;
        }
        if (priceProduct < 0) {
            Alert negativePrice = new Alert(Alert.AlertType.ERROR);
            negativePrice.setTitle("Error Message");
            negativePrice.setContentText("Price cannot be a negative number");
            negativePrice.showAndWait();
            return;
        }
        if (productNamePrompt.getText().equals("")) {
            Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
            alertEmptyName.setTitle("Error Message");
            alertEmptyName.setContentText("Products must have names.");
            alertEmptyName.showAndWait();
            return;
        }
        if (stockProduct < minProduct || stockProduct > maxProduct) {
            Alert stockLimit = new Alert(Alert.AlertType.ERROR);
            stockLimit.setTitle("Error Message");
            stockLimit.setContentText("Inventory level must fall between the minimum and maximum values.");
            stockLimit.showAndWait();
            return;
        }
        if (currentAssociatedPart.size() <= 0) {
            Alert alertEmpty = new Alert(Alert.AlertType.ERROR);
            alertEmpty.setTitle("Error Message");
            alertEmpty.setContentText("Products require one or more associated parts.");
            alertEmpty.showAndWait();
            return;
        }

     
        product.setId(idProduct);
        product.setStock(stockProduct);
        product.setName(namedProduct);
        product.setPrice(priceProduct);
        product.setMax(maxProduct);
        product.setMin(minProduct);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void searchPart(MouseEvent event) {
        if (!searchPartField.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            Model.inventory.getAllParts().stream().filter((p) -> (p.getName().contains(searchPartField.getText().trim()))).forEachOrdered((p) -> {
                partsInventorySearch.add(p);
            });

            partsTable.setItems(partsInventorySearch);
            partsTable.refresh();

        }
    }

    @FXML
    void clearText(MouseEvent event) {
        {
            Object source = event.getSource();
            TextField field = (TextField) source;
            field.setText("");
            if (searchPartField == field) {
                if (partInventory.size() != 0) {
                    partsTable.setItems(partInventory);
                }
            }
            if (searchPartField == field) {
                if (partInventory.size() != 0) {
                    partsTable.setItems(partInventory);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productIdPrompt.setDisable(true);
        for (Product product : inventory.getAllProducts()) {
            if (product.getId() > productId) {
                productId = product.getId();
            }

        }
        ++productId;
        product = new Product(productId, null, 0.0, 0, 0, 0);

        inventory.addProduct(product);

        productIdPrompt.setText(String.valueOf(productId));
        partsTable.setItems(inventory.getAllParts());
        // associatedPartsTable.setItems(product.getAllAssociatedParts());
        partInventory.setAll(Model.inventory.getAllParts());
        associatedPartsTable.setItems(currentAssociatedPart);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePartCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
