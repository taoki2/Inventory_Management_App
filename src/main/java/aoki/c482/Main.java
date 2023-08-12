package aoki.c482;
import aoki.c482.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/** FUTURE ENHANCEMENT: Subtract and add from the part's stock/inv level when adding and removing
 * the associated part to provide the current stock level for each part in realtime.
 * This class creates the main application.
 * Javadoc files for this project are located in the '/C482/docs/' folder */
public class Main extends Application {
    /**
     * This method creates the stage and loads the initial fxml scene for the application.
     * @param stage the main stage*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory System 2.0");
        stage.setScene(scene);
        stage.show();
    }

/** This is the main method. This is the entry point and the first method that is called.*/
    public static void main(String[] args) {
/*        Part part1 = new InHouse(101, "Name1", 9.99, 10, 1, 30, 22);
        Part part2 = new Outsourced(102, "name2", 10.34, 5, 1, 10, "company1");
        Product product1 = new Product(103, "prod1", 9.99, 10, 1, 25);
        Product product2 = new Product(104, "Prod3", 19.99, 110, 1, 125);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);*/
        launch(args);
    }
}
