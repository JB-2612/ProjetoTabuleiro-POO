module com.example.jogo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jogo to javafx.fxml;
    exports com.example.jogo;
    exports com.example.jogo.controller;
    opens com.example.jogo.controller to javafx.fxml;
}