module org.example.libmgmt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.libmgmt to javafx.fxml;
    exports org.example.libmgmt;
}