module com.example.filechooserimg {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.filechooserimg to javafx.fxml;
    exports com.example.filechooserimg;
}