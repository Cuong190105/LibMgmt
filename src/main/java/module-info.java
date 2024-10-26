module org.example.libmgmt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.libmgmt to javafx.fxml;
    exports org.example.libmgmt;
}