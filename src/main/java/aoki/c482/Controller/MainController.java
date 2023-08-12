package aoki.c482.Controller;
import aoki.c482.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class is the main screen that displays the parts and products. */
public class MainController implements Initializable {
    @FXML private TextField searchPartTxt;
    @FXML private TextField searchProductTxt;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partStockCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productStockCol;
    @FXML private TableColumn<Product, Double> productPriceCol;

    /** Opens the add part screen when the add button is clicked. */
    @FXML void onActionAddPart(ActionEvent event) throws IOException {
        ChangeScene(event, "add-part-view.fxml");
    }
    /** Opens the modify part screen when the modify button is clicked. */
    @FXML void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/aoki/c482/View/modify-part-view.fxml"));
            loader.load();
            ModifyPartController ADMController = loader.getController();
            ADMController.modifyPartDetails(partTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            MainController.AlertMessage("You must first select a part to modify.");
        }
    }
    /** Deletes the selected part. */
    @FXML void onActionDeletePart(ActionEvent event) {
        if (partTableView.getSelectionModel().isEmpty()) {
            MainController.AlertMessage("You must first select a part to delete.");
        }
        else {
            Part part = partTableView.getSelectionModel().getSelectedItem();
            String confirmationMessage = "Are you sure you want to delete this part?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
                partTableView.setItems(Inventory.getAllParts());
            }
        }
    }
    /** Searches for the part ID or part name when text is entered into the search field and the user hits return. */
    @FXML void onActionSearchPart(ActionEvent event) {
        String filter = String.valueOf(searchPartTxt.getText());
        if (filter == "")
            partTableView.getSelectionModel().clearSelection();
        try {
            Integer.parseInt(filter);
            partTableView.setItems(Inventory.getAllParts());
            SearchPartId(Integer.valueOf(filter));
        }
        catch (Exception e) {
            partTableView.setItems(Inventory.lookupPart(filter));
        }
    }
    /** Opens the add product screen when the add button is clicked. */
    @FXML void onActionAddProduct(ActionEvent event) throws IOException {
        ChangeScene(event, "add-product-view.fxml");
    }
    /** Opens the modify product screen when the modify button is clicked. */
    @FXML void onActionModifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/aoki/c482/View/modify-product-view.fxml"));
            loader.load();
            ModifyProductController ADMController = loader.getController();
            ADMController.modifyProductDetails(productTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            MainController.AlertMessage("You must first select a product to modify.");
        }
    }
    /** Deletes the selected product if the product does not have any associated parts */
    @FXML void onActionDeleteProduct(ActionEvent event) {
        if (productTableView.getSelectionModel().isEmpty()) {
            MainController.AlertMessage("You must first select a product to delete.");
        }
        else {
            Product product = productTableView.getSelectionModel().getSelectedItem();
            if (product.getAllAssociatedParts().isEmpty()) {
                String confirmationMessage = "Are you sure you want to delete this product?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(product);
                    productTableView.setItems(Inventory.getAllProducts());
                }
            }
            else
                MainController.AlertMessage("This product has associated parts that you must remove first.");
        }
    }
    /** Searches for the product ID or product name when text is entered into the search field and the user hits return. */
    @FXML void onActionSearchProduct(ActionEvent event) {
        String filter = String.valueOf(searchProductTxt.getText());
        if (filter == "")
            productTableView.getSelectionModel().clearSelection();
        try {
            Integer.parseInt(filter);
            productTableView.setItems(Inventory.getAllProducts());
            SearchProductId(Integer.valueOf(filter));
        }
        catch (Exception e) {
            productTableView.setItems(Inventory.lookupProduct(filter));
        }
    }
    /** Exits the application when the exit button is clicked. */
    @FXML void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    /** Changes the scene.
     * @param fxml location of the fxml view to switch to */
    public static void ChangeScene(ActionEvent event, String fxml) throws IOException {
        fxml = "/aoki/c482/View/" + fxml;
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(MainController.class.getResource(fxml));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Searches for the part ID and highlights the row if found.
     * @param id the part ID to search for
     * @return true if part ID is found; false if part ID is not found.*/
    public boolean SearchPartId(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                partTableView.getSelectionModel().select(index);
                return true;
            }
        }
        partTableView.getSelectionModel().clearSelection();
        MainController.InfoMessage("No matching search results were found.");
        return false;
    }
    /** Searches for the product ID and highlights the row if found.
     * @param id the product ID to search for
     * @return true if product ID is found; false if product ID is not found.*/
    public boolean SearchProductId(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                productTableView.getSelectionModel().select(index);
                return true;
            }
        }
        productTableView.getSelectionModel().clearSelection();
        MainController.InfoMessage("No matching search results were found.");
        return false;
    }
    /** Creates a popup error message.
     * @param message Error message to display */
    public static void AlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /** Creates a popup information message.
     * @param message Information message to display */
    public static void InfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /** The first method that is run */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

