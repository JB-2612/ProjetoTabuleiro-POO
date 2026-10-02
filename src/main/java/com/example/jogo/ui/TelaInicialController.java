package com.example.jogo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaInicialController {
    @FXML Button btn1;
    @FXML Button btn2;
    @FXML TextField campotexto1;

    @FXML public void aviso(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO!");
        alerta.setHeaderText("ATENÇÃO!!!");
        alerta.setContentText("Botão clicado com sucesso!!");

        alerta.showAndWait();
    }
    @FXML public void enviar(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Nome do livro");
        alerta.setHeaderText(null);
        alerta.setContentText(campotexto1.getText());

        alerta.showAndWait();
    }

}
