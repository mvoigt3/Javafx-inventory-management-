package View_Controller;

import Model.Inhouse;
import Model.inventory;
import Model.Part;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AddPartController implements Initializable {

    static inventory inv;
    int partId;
    Part part;
    @FXML
    public RadioButton addOutSourced;

    @FXML
    private RadioButton addInHouse;

    @FXML
    private Label radioButtonLabel;

    @FXML
    public void inHouseDisplay(MouseEvent event) {
        radioButtonLabel.setText("Machine ID");
        radioButtonLabelPrompt.setPromptText("Machine ID");

    }

    @FXML
    public void outSourcedDisplay(MouseEvent event) {
        radioButtonLabel.setText("Company Name");
        radioButtonLabelPrompt.setPromptText("Company Name");

    }

    @FXML
    private ToggleGroup invType;

    @FXML
    private Label addPartIDLabel;

    @FXML
    private Label addPartNameLabel;

    @FXML
    private Label addPartInvLabel;

    @FXML
    private Label addPartPriceLabel;

    @FXML
    private Label addPartMaxLabel;

    @FXML
    private TextField addPartNamePrompt;

    @FXML
    private TextField addPartInvPrompt;

    @FXML
    private TextField addPartPricePrompt;

    @FXML
    private TextField addPartMaxPrompt;

    @FXML
    private TextField addPartMinPrompt;

    @FXML
    private Label addPartMinLabel;

    @FXML
    private TextField radioButtonLabelPrompt;

    @FXML
    private TextField addPartIDPrompt;

    @FXML
    private Button addPartSaveButton;

    @FXML
    private Button addPartDeleteButton;
    @FXML
    private Button addPartCancel;

    @FXML
    void addPartCancellCall(MouseEvent event) {

    }

    @FXML
    void addPartCancelCall(MouseEvent event) throws IOException {
        Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alertConfirmCancel.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
    void saveAddPart(MouseEvent event) throws IOException {
        int id = Integer.parseInt(addPartIDPrompt.getText());
     

        try {

            if (addInHouse.isSelected()) {
                if (!radioButtonLabelPrompt.getText().matches("[0-9]*")) {
                    Alert errorString = new Alert(Alert.AlertType.ERROR);
                    errorString.setTitle("Error Message");
                    errorString.setContentText("In house Parts require numbers only for 'Machine Id's' ");
                    errorString.showAndWait();

                }
                int idPart = Integer.parseInt(addPartIDPrompt.getText());
                String namePart = addPartNamePrompt.getText();
                int stockPart = Integer.parseInt(addPartInvPrompt.getText());
                double pricePart = Double.parseDouble(addPartPricePrompt.getText());
                int maxPart = Integer.parseInt(addPartMaxPrompt.getText());
                int minPart = Integer.parseInt(addPartMinPrompt.getText());
                int machineId = Integer.parseInt(radioButtonLabelPrompt.getText());
                if (minPart > maxPart) {
                    Alert alertInventoryLimit = new Alert(Alert.AlertType.ERROR);
                    alertInventoryLimit.setTitle("Error Message");
                    alertInventoryLimit.setContentText("Minimum must be less than maximum!");
                    alertInventoryLimit.showAndWait();
                    return;
                }
                if (pricePart < 0) {
                    Alert negativePrice = new Alert(Alert.AlertType.ERROR);
                    negativePrice.setTitle("Error Message");
                    negativePrice.setContentText("Price cannot be a negative number");
                    negativePrice.showAndWait();
                    return;
                }
                if (addPartNamePrompt.getText().equals("")) {
                    Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
                    alertEmptyName.setTitle("Error Message");
                    alertEmptyName.setContentText("Parts must have names.");
                    alertEmptyName.showAndWait();
                    return;
                }
                if (stockPart < minPart || stockPart > maxPart) {
                    Alert stockLimit = new Alert(Alert.AlertType.ERROR);
                    stockLimit.setTitle("Error Message");
                    stockLimit.setContentText("Inventory level must fall between the minimum and maximum values.");
                    stockLimit.showAndWait();
                    return;
                }

                Model.inventory.addPart(new Inhouse(idPart, namePart, pricePart, stockPart, minPart, maxPart, machineId));
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
            if (addOutSourced.isSelected()) {

                int idPart = Integer.parseInt(addPartIDPrompt.getText());
                String namePart = addPartNamePrompt.getText();
                int stockPart = Integer.parseInt(addPartInvPrompt.getText());
                double pricePart = Double.parseDouble(addPartPricePrompt.getText());
                int maxPart = Integer.parseInt(addPartMaxPrompt.getText());
                int minPart = Integer.parseInt(addPartMinPrompt.getText());
                String companyName = radioButtonLabelPrompt.getText();
                if (minPart > maxPart) {
                    Alert alertInventoryLimit = new Alert(Alert.AlertType.ERROR);
                    alertInventoryLimit.setTitle("Error Message");
                    alertInventoryLimit.setContentText("Minimum must be less than maximum!");
                    alertInventoryLimit.showAndWait();
                    return;
                }
                if (pricePart < 0) {
                    Alert negativePrice = new Alert(Alert.AlertType.ERROR);
                    negativePrice.setTitle("Error Message");
                    negativePrice.setContentText("Price cannot be a negative number");
                    negativePrice.showAndWait();
                    return;
                }
                if (addPartNamePrompt.getText().equals("")) {
                    Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
                    alertEmptyName.setTitle("Error Message");
                    alertEmptyName.setContentText("Parts must have names.");
                    alertEmptyName.showAndWait();
                    return;
                }
                if (stockPart < minPart || stockPart > maxPart) {
                    Alert stockLimit = new Alert(Alert.AlertType.ERROR);
                    stockLimit.setTitle("Error Message");
                    stockLimit.setContentText("Inventory level must fall between the minimum and maximum values.");
                    stockLimit.showAndWait();
                    return;
                }
                if (radioButtonLabelPrompt.getText().equals("")) {
                    Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
                    alertEmptyName.setTitle("Error Message");
                    alertEmptyName.setContentText("Outsourced Parts must have company names");
                    alertEmptyName.showAndWait();
                    return;
                }

                Model.inventory.addPart(new Outsourced(idPart, namePart, pricePart, stockPart, minPart, maxPart, companyName));
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

        } catch (IOException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartIDPrompt.setDisable(true);
        for (Part part : inventory.getAllParts()) {
            if (part.getId() > partId) {
                partId = part.getId();
            }

        }
        ++partId;
        addPartIDPrompt.setText(String.valueOf(partId));
    }

}
