module com.example.jogo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jogo to javafx.fxml;
    exports com.example.jogo;
    exports com.example.jogo.ui;
    opens com.example.jogo.ui to javafx.fxml;
    exports com.example.jogo.jogador;
    opens com.example.jogo.jogador to javafx.fxml;
    exports com.example.jogo.casa;
    opens com.example.jogo.casa to javafx.fxml;
}