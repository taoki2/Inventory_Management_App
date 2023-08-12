package aoki.c482.Controller;
import aoki.c482.Model.InHouse;
import aoki.c482.Model.Inventory;
import aoki.c482.Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static java.lang.Math.round;
/** This class creates new parts. */
public class AddPartController implements Initializable {
    @FXML private Label machineIdLbl;
    @FXML private TextField machineIdTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField stockTxt;
    @FXML private RadioButton inHouseRBtn;

    /** Sets the label to "Machine ID" when InHouse radio button is selected. */
    @FXML void onActionInHouse(ActionEvent event) {
        machineIdLbl.setText("Machine ID");
    }
    /** Sets the label to "Company Name" when Outsourced radio button is selected. */
    @FXML void onActionOutsourced(ActionEvent event) {
        machineIdLbl.setText("Company Name");
    }
    /** Saves the new part when the save button is clicked. */
    @FXML void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Inventory.generateId();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(stockTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            price = round(price * 100.0) / 100.0;
            if (min <= max) {
                if (stock >= min && stock <= max) {
                    boolean isInHouse;
                    isInHouse = inHouseRBtn.isSelected();
                    if (isInHouse) {
                        int machineId = Integer.parseInt(machineIdTxt.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    } else {
                        String companyName = machineIdTxt.getText();
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                    }
                    MainController.ChangeScene(event, "main-view.fxml");
                }
                else
                    MainController.AlertMessage("Inv must be between Min and Max.");
            }
            else
                MainController.AlertMessage("Min must not be greater than Max.");
        }
        catch(NumberFormatException e) {
            MainController.AlertMessage("Please enter valid values into the text fields.");
        }
    }
    /** Returns to the main application screen when the cancel button is clicked. */
    @FXML void onActionMainView(ActionEvent event) throws IOException {
        MainController.ChangeScene(event, "main-view.fxml");
    }
    /** The first method that is run */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}