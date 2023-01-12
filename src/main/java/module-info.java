module com.example.mp_mijomi_cafe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mp_mijomi_cafe to javafx.fxml;
    exports com.example.mp_mijomi_cafe;
}