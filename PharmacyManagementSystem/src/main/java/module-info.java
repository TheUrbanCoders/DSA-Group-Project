module com.example.pharmacymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pharmacymanagementsystem to javafx.fxml;
    exports com.example.pharmacymanagementsystem;
}