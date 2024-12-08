module org.example.libmgmt {
    requires javafx.controls;
    requires javafx.fxml;
  requires jdk.httpserver;
  requires javafx.swing;
  requires org.apache.pdfbox;
  requires com.google.gson;
  requires java.sql;


  opens org.example.libmgmt to javafx.fxml;
    exports org.example.libmgmt;
    exports org.example.libmgmt.DB;
    opens org.example.libmgmt.DB to javafx.fxml;
}