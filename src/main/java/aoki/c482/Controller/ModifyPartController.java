package aoki.c482.Controller;

import aoki.c482.Model.InHouse;
import aoki.c482.Model.Inventory;
import aoki.c482.Model.Outsourced;
import aoki.c482.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;
/** This class modifies parts */
public class ModifyPartController implements Initializable {
    @FXML private Label machineIdLbl;
    @FXML private TextField idTxt;
    @FXML private TextField machineIdTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField stockTxt;
    @FXML private RadioButton inHouseRBtn;
    @FXML private RadioButton outsourcedRBtn;
    /** Sets the label to "Machine ID" when InHouse radio button is selected. */
    @FXML void onActionInHouse(ActionEvent event) {
        machineIdLbl.setText("Machine ID");
    }
    /** Sets the label to "Company Name" when Outsourced radio button is selected. */
    @FXML void onActionOutsourced(ActionEvent event) {
        machineIdLbl.setText("Company Name");
    }
    /** Updates the part details when the save button is clicked. */
    @FXML void onActionUpdatePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(idTxt.getText());
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
                        Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId));
                    } else {
                        String companyName = machineIdTxt.getText();
                        Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
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
    /** Receives the part details of the part to be modified.
     * @param part The part that was selected on the main screen. */
    public void modifyPartDetails(Part part) {
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        stockTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));
        if (part instanceof InHouse) {
            machineIdTxt.setText(String.valueOf(((InHouse)part).getMachineId()));
            inHouseRBtn.setSelected(true);
            machineIdLbl.setText("Machine ID");
        }
        else {
            machineIdTxt.setText(((Outsourced)part).getCompanyName());
            outsourcedRBtn.setSelected(true);
            machineIdLbl.setText("Company Name");
        }
    }

    /** The first method that is run */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}