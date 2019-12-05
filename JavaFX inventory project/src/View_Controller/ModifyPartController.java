package View_Controller;

import View_Controller.MainScreenController;

import Model.Inhouse;
import Model.inventory;
import Model.Part;
import Model.Outsourced;
import Model.Part;
import Model.Outsourced;
import static com.sun.deploy.util.ReflectionUtil.instanceOf;

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
import static javafx.scene.input.KeyCode.A;

public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton modifyRadioOutsourced;

    @FXML
    private ToggleGroup modifyToggle;

    @FXML
    private RadioButton modifyRadioInhouse;

    @FXML
    private Label modifyChangeText;

    @FXML
    private TextField modifyNamePrompt;

    @FXML
    private TextField modifyInvPrompt;

    @FXML
    private TextField modifyPricePrompt;

    @FXML
    private TextField modifyMaxPrompt;

    @FXML
    private TextField modifyMinPrompt;

    @FXML
    private TextField modifyChangeTextPrompt;

    @FXML
    private TextField modifyIdPrompt;

    @FXML
    private Button saveModifyPartbutton;

    @FXML
    private Button modifyCancelCall;

    @FXML
    void cancelModifyPart(MouseEvent event) throws IOException {
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
    public void modifyOutsourcedSelect(MouseEvent event) {
        modifyChangeTextPrompt.clear();
        modifyChangeText.setText("Company Name");
        modifyChangeTextPrompt.setPromptText("Company Name");
    }

    @FXML
    public void modifyInhouseSelect(MouseEvent Event) {
        modifyChangeTextPrompt.clear();
        modifyChangeText.setText("Machine ID");
        modifyChangeTextPrompt.setPromptText("Machine ID");
    }

    public void sendModifyPart(Part thePart) {

        modifyIdPrompt.setDisable(true);
        modifyIdPrompt.setText(String.valueOf(thePart.getId()));
        modifyNamePrompt.setText(thePart.getName());
        modifyInvPrompt.setText(String.valueOf(thePart.getStock()));
        modifyPricePrompt.setText(String.valueOf(thePart.getPrice()));
        modifyMaxPrompt.setText(String.valueOf(thePart.getMax()));
        modifyMinPrompt.setText(String.valueOf(thePart.getMin()));
        if (thePart instanceof Outsourced) {
            modifyRadioOutsourced.setSelected(true);
            modifyChangeText.setText("Company Name");
            modifyChangeTextPrompt.setText(((Outsourced) thePart).getCompanyName());

        }
        if (thePart instanceof Inhouse) {
            modifyRadioInhouse.setSelected(true);
            modifyChangeText.setText("Machine Id");
            modifyChangeTextPrompt.setText(String.valueOf(((Inhouse) thePart).getMachineId()));
        }

    }

    @FXML
    public void saveModifyPart(MouseEvent event) throws IOException {

        Double pricePart = Double.parseDouble(modifyPricePrompt.getText());
        int stockPart = Integer.parseInt(modifyInvPrompt.getText());
        String minNumber = modifyMinPrompt.getText();
        int minResult = Integer.parseInt(minNumber);
        String maxNumber = modifyMaxPrompt.getText();
        int maxResult = Integer.parseInt(maxNumber);
        Integer id = Integer.parseInt(modifyIdPrompt.getText());
        Part thePart = null;
        if (minResult > maxResult) {
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
        if (modifyNamePrompt.getText().equals("")) {
            Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
            alertEmptyName.setTitle("Error Message");
            alertEmptyName.setContentText("Parts must have names.");
            alertEmptyName.showAndWait();
            return;
        }
        if (stockPart < minResult || stockPart > maxResult) {
            Alert stockLimit = new Alert(Alert.AlertType.ERROR);
            stockLimit.setTitle("Error Message");
            stockLimit.setContentText("Inventory level must fall between the minimum and maximum values.");
            stockLimit.showAndWait();
            return;
        }
        for (Part part : inventory.getAllParts()) {

            if (part.getId() == id) {
                thePart = part;

            }

        }

        if (modifyRadioInhouse.isSelected()) {
            if (!modifyChangeTextPrompt.getText().matches("[0-9]*")) {
                Alert errorString = new Alert(Alert.AlertType.ERROR);
                errorString.setTitle("Error Message");
                errorString.setContentText("In house Parts require numbers only for 'Machine Id's' ");
                errorString.showAndWait();

            }
            int HID = Integer.parseInt(modifyIdPrompt.getText());
            String HName = modifyNamePrompt.getText();
            Double HPRICE = Double.parseDouble(modifyPricePrompt.getText());
            int HSTOCK = Integer.parseInt(modifyInvPrompt.getText());
            int HMAX = Integer.parseInt(modifyMaxPrompt.getText());
            int HMIN = Integer.parseInt(modifyMinPrompt.getText());
            int HMAC = Integer.parseInt(modifyChangeTextPrompt.getText());

            Model.inventory.deletePart(thePart);
            Model.inventory.addPart((new Inhouse(HID, HName, HPRICE, HSTOCK, HMIN, HMAX, HMAC)));

        }

        if (modifyRadioOutsourced.isSelected()) {
            if (modifyChangeTextPrompt.getText().equals("")) {
                Alert alertEmptyName = new Alert(Alert.AlertType.ERROR);
                alertEmptyName.setTitle("Error Message");
                alertEmptyName.setContentText("Outsourced Parts must have company names");
                alertEmptyName.showAndWait();
                return;
            }
            int OID = Integer.parseInt(modifyIdPrompt.getText());
            String OName = modifyNamePrompt.getText();
            Double OPRICE = Double.parseDouble(modifyPricePrompt.getText());
            int OSTOCK = Integer.parseInt(modifyInvPrompt.getText());
            int OMAX = Integer.parseInt(modifyMaxPrompt.getText());
            int OMIN = Integer.parseInt(modifyMinPrompt.getText());
            String OCOMP = modifyChangeTextPrompt.getText();

            Model.inventory.deletePart(thePart);
            Model.inventory.addPart((new Outsourced(OID, OName, OPRICE, OSTOCK, OMIN, OMAX, OCOMP)));

        }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
