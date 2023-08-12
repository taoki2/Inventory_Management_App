package aoki.c482.Controller;

import aoki.c482.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Math.round;
/** This class modifies products */
public class ModifyProductController implements Initializable {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML private TableView<Part> addPartTableView;
    @FXML private TableColumn<Part, Integer> addPartIdCol;
    @FXML private TableColumn<Part, String> addPartNameCol;
    @FXML private TableColumn<Part, Double> addPriceCol;
    @FXML private TableColumn<Part, Integer> addStockCol;
    @FXML private TextField idTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;
    @FXML private TextField nameTxt;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private TableColumn<Part, Integer> stockCol;
    @FXML private TextField priceTxt;
    @FXML private TextField searchTxt;
    @FXML private TextField stockTxt;
    /** Returns to the main application screen when the cancel button is clicked. */
    @FXML void onActionMainView(ActionEvent event) throws IOException {
        MainController.ChangeScene(event, "main-view.fxml");
    }
    /** Updates the product details when the save button is clicked. */
    @FXML void onActionUpdateProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(stockTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            price = round(price * 100.0) / 100.0;
            Product product = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            if (min <= max) {
                if (stock >= min && stock <= max) {
                    Inventory.updateProduct(id, product);
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
    /** Adds an associated part when the add button is clicked. */
    @FXML void onActionAddPart(ActionEvent event) throws IOException {
        Part part = addPartTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
    }
    /** Removes an associated part when the remove associated part button is clicked. */
    @FXML void onActionRemovePart(ActionEvent event) throws IOException {
        if (partTableView.getSelectionModel().isEmpty()) {
            MainController.AlertMessage("You must first select a part to delete.");
        }
        else {
            Part part = partTableView.getSelectionModel().getSelectedItem();
            String confirmationMessage = "Are you sure you want to delete this part?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for(int i = 0; i < associatedParts.size(); i++) {
                    if (associatedParts.get(i).getId() == part.getId()) {
                        associatedParts.remove(i);
                        partTableView.setItems(associatedParts);
                    }
                }
            }
        }
    }
    /** Searches for the part ID or part name when text is entered into the search field and the user hits return. */
    @FXML void onActionSearchPart(ActionEvent event) {
        String filter = String.valueOf(searchTxt.getText());
        if (filter == "")
            addPartTableView.getSelectionModel().clearSelection();
        try {
            Integer.parseInt(filter);
            addPartTableView.setItems(Inventory.getAllParts());
            SearchPartId(Integer.valueOf(filter));
        }
        catch (Exception e) {
            addPartTableView.setItems(Inventory.lookupPart(filter));
        }
    }
/** Receives the product details of the product to be modified.
 * @param product The product that was selected on the main screen. */
    public void modifyProductDetails(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        stockTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));
        for (Part part : product.getAllAssociatedParts()) {
            associatedParts.add(part);
        }
    }
    /** Searches for the part ID and highlights the row if found.
     * @param id the part ID to search for
     * @return true if part ID is found; false if part ID is not found.*/
    public boolean SearchPartId(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                addPartTableView.getSelectionModel().select(index);
                return true;
            }
        }
        addPartTableView.getSelectionModel().clearSelection();
        MainController.InfoMessage("No matching search results were found.");
        return false;
    }
    /** The first method that is run */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartTableView.setItems(Inventory.getAllParts());
        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView.setItems(associatedParts);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}