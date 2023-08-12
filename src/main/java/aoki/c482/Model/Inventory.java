package aoki.c482.Model;
import aoki.c482.Controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int uniqueId;

    /** Adds a part.
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Returns the list of parts.
     * @return the list of parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Adds a product.
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Returns the list of products.
     * @return the list of products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Returns the list of filtered parts IDs.
     * @param partId the part ID to filter
     * @return the list of filtered parts
     */
    public static ObservableList<Part> lookupPart(int partId) {
        ObservableList<Part> filteredPart = FXCollections.observableArrayList();
        filteredPart.clear();
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId)
                filteredPart.add(part);
        }
        if (filteredPart.isEmpty()) {
            MainController.InfoMessage("No matching search results were found.");
            return allParts;
        }
        else
            return filteredPart;
    }

    /**Returns the list of filtered part names.
     * @param partName the part name to filter
     * @return the list of filtered parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredPart = FXCollections.observableArrayList();
        filteredPart.clear();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName))
                filteredPart.add(part);
        }
        if (filteredPart.isEmpty()) {
            MainController.InfoMessage("No matching search results were found.");
            return allParts;
        }
        else
            return filteredPart;
    }

    /** RUNTIME ERROR: trying to delete a part without selecting a part led to a NullPointerException.
     * To correct this, logic was added in the main controller class to check if a part was
     * selected first before calling this deletePart() method.
     * Deletes a part.
     * @param selectedPart the part to be deleted
     * @return true after the part is deleted */
    public static boolean deletePart(Part selectedPart) {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == selectedPart.getId())
                return Inventory.getAllParts().remove(part);
        }
        return false;
    }
    /** Update the part details for a part
     * @param index the id of the part
     * @param  selectedPart the part to be updated
     * @return true after the part is updated */
    public static boolean updatePart(int index, Part selectedPart) {
        int tableIndex = -1;
        for(Part parts : Inventory.getAllParts()) {
            tableIndex++;
            if(parts.getId() == index) {
                Inventory.getAllParts().set(tableIndex, selectedPart);
                return true;
            }
        }
        return false;
    }

    /**Returns the list of filtered product IDs.
     * @param productId the product ID to filter
     * @return the list of filtered products
     */
    public static ObservableList<Product> lookupProduct(int productId) {
        ObservableList<Product> filteredProduct = FXCollections.observableArrayList();
        filteredProduct.clear();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId)
                filteredProduct.add(product);
        }
        if (filteredProduct.isEmpty()) {
            MainController.InfoMessage("No matching search results were found.");
            return allProducts;
        }
        else
            return filteredProduct;
    }

    /**Returns the list of filtered products.
     * @param productName the product name to filter
     * @return the list of filtered products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProduct = FXCollections.observableArrayList();
        filteredProduct.clear();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName))
                filteredProduct.add(product);
        }
        if (filteredProduct.isEmpty()) {
            MainController.InfoMessage("No matching search results were found.");
            return allProducts;
        }
        else
            return filteredProduct;
    }
    /** Deletes a product
     * @param selectedProduct the product to be deleted
     * @return true after the product is deleted */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }
    /** Update the product details for a product
     * @param index the id of the product
     * @param  newProduct the product to be updated
     * @return true after the product is updated */
    public static boolean updateProduct(int index, Product newProduct) {
        int tableIndex = -1;
        for(Product products : Inventory.getAllProducts()) {
            tableIndex++;
            if(products.getId() == index) {
                Inventory.getAllProducts().set(tableIndex, newProduct);
                return true;
            }
        }
        return false;
    }

    /** Generates a unique ID for a part or product
     * @return the unique ID for a part or product
     */
    public static int generateId() {
        uniqueId++;
        return uniqueId;
    }
}