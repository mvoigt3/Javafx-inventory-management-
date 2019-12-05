package View_Controller;

import Model.Product;
import Model.Part;
import Model.inventory;
import View_Controller.AddPartController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ModifyProductController implements Initializable {
    Product product;
    public ObservableList<Part> partInventory = FXCollections.observableArrayList();

 public ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
 private ObservableList<Part> currentParts = FXCollections.observableArrayList();

    
    @FXML
    private Button deleteAssoc;

    @FXML
    private TextField stockPrompt;

    @FXML
    private TextField searchPartField;

    @FXML
    private  TextField idPrompt;

    @FXML
    private Button searchPart;

    @FXML
    private Button addPartMod;

    @FXML
    private Button saveModifiedButton;

    @FXML
    private TableView<Part> newAssocTable;

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
    private Button cancelModify;

    @FXML
    private TextField namePrompt;

    @FXML
    private TextField pricePrompt;

    @FXML
    private TextField minPrompt;

    @FXML
    private TextField maxPrompt;

    @FXML
    void addPart(MouseEvent event) {
 
    Part associationPart =partsTable.getSelectionModel().getSelectedItem();
    boolean duplicate=false;
    
    if(associationPart!=null){
        int id= associationPart.getId();
        for (int i =0;i<currentParts.size();i++){
            if (currentParts.get(i).getId()==id){
                duplicate = true;
                
            }
        }
  
            if(duplicate){
                Alert duplicated = new Alert(Alert.AlertType.ERROR);
                duplicated.setTitle("Error Message");
                duplicated.setContentText("This part is already associated!");
                duplicated.showAndWait();
                return;
       }    
            if(!duplicate)

            currentParts.add(associationPart);
        }
     product.addAssociatedPart(associationPart);
    }
    
 
    
    
    @FXML
    void assocDelete(MouseEvent event) {
         Alert alertConfirmDelete = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this part for this product?");
        Optional<ButtonType> result =alertConfirmDelete.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
    Part associationPart =newAssocTable.getSelectionModel().getSelectedItem();
    
      currentParts.remove(associationPart);
    }
    }

    @FXML
    void cancelModifyProduct(MouseEvent event) throws IOException {
         Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel?");
        Optional<ButtonType> result =alertConfirmCancel.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
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
    void saveModify(MouseEvent event) throws IOException {
        int idProduct = Integer.parseInt(idPrompt.getText());
                String namedProduct = namePrompt.getText();
                int stockProduct = Integer.parseInt(stockPrompt.getText());
                double priceProduct = Double.parseDouble(pricePrompt.getText());
      String minNumber = minPrompt.getText();
        int minResult = Integer.parseInt(minNumber);
     String maxNumber = maxPrompt.getText();
      int maxResult = Integer.parseInt(maxNumber);
     Integer id =Integer.parseInt(idPrompt.getText());
     Product theProduct =null;
     if(minResult>maxResult){
                Alert alertInventoryLimit = new Alert(Alert.AlertType.ERROR);
                alertInventoryLimit.setTitle("Error Message");
                alertInventoryLimit.setContentText("Minimum must be less than maximum!");
                alertInventoryLimit.showAndWait();
                return;
            }
   
              if(priceProduct<0){
                Alert negativePrice = new Alert(Alert.AlertType.ERROR);
                negativePrice.setTitle("Error Message");
                negativePrice.setContentText("Price cannot be a negative number");
                negativePrice.showAndWait();
                return;
              }
              if(namePrompt.getText().equals("")){
                  Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
                alertEmptyName.setTitle("Error Message");
                alertEmptyName.setContentText("Products must have names.");
                alertEmptyName.showAndWait();
                return;
              }
              if (stockProduct<minResult || stockProduct>maxResult){
                   Alert stockLimit = new Alert(Alert.AlertType.ERROR);
                stockLimit.setTitle("Error Message");
                stockLimit.setContentText("Inventory level must fall between the minimum and maximum values.");
                stockLimit.showAndWait();
                return;
              }
              if(currentParts.size()<=0){
                  Alert alertEmpty = new Alert(Alert.AlertType.ERROR);
                alertEmpty.setTitle("Error Message");
                alertEmpty.setContentText("Products require one or more associated parts.");
                alertEmpty.showAndWait();
                return;
              }
                
 
         for (Product product : inventory.getAllProducts()) {
          
            if(product.getId() == id) {
                theProduct = product;
              
          
                 }
           
            
         }
       
      
        
        
        
        theProduct.setName(namePrompt.getText());

        theProduct.setStock(Integer.parseInt(stockPrompt.getText()));
        theProduct.setPrice(Double.parseDouble(pricePrompt.getText()));
        theProduct.setMax(Integer.parseInt(maxPrompt.getText()));
        theProduct.setMin(Integer.parseInt(minPrompt.getText()));
        theProduct.setAssociatedParts(currentParts);
        inventory.updatedProduct(inventory.getAllProducts().indexOf(id),theProduct);


   
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


    public  void sendModifyProduct(Product product){
        idPrompt.setDisable(true);
        idPrompt.setText(String.valueOf(product.getId()));
        namePrompt.setText(product.getName());
        stockPrompt.setText(String.valueOf(product.getStock()));
        pricePrompt.setText(String.valueOf(product.getPrice()));
        maxPrompt.setText(String.valueOf(product.getMax()));
        minPrompt.setText(String.valueOf(product.getMin()));
        currentParts.addAll(product.getAllAssociatedParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    partsTable.setItems(inventory.getAllParts());
    partInventory.setAll(Model.inventory.getAllParts());

    newAssocTable.setItems(currentParts);
   
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



