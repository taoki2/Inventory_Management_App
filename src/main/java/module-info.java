module aoki.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens aoki.c482 to javafx.fxml;
    exports aoki.c482;
    exports aoki.c482.Controller;
    exports aoki.c482.Model;

    opens aoki.c482.Controller to javafx.fxml;
    opens aoki.c482.Model to javafx.fxml;

}