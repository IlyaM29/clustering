module com.example.clustering {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clustering to javafx.fxml;
    exports com.example.clustering;
}