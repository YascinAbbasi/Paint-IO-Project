module com.example.paintioproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.paintioproject to javafx.fxml;
    exports com.example.paintioproject;
}