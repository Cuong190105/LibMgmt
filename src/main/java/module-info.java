module org.example.libmgmt {
    requires javafx.controls;
    requires javafx.fxml;
  requires java.sql.rowset;
  requires javafx.swing;


  opens org.example.libmgmt to javafx.fxml;
    exports org.example.libmgmt;
    exports org.example.libmgmt.DB;
    opens org.example.libmgmt.DB to javafx.fxml;
}