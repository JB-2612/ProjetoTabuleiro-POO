package com.example.jogo.ui;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TelaResultadoController {

    @FXML private ImageView imgVencedor;
    @FXML private Label lblVencedor;
    @FXML private TextArea txtResultado;

    private static final String[] CORES = {"Azul", "Verde", "Amarelo", "Branco", "Vermelho", "Preto"};
    private static final String[] IMAGENS = {
            "pino_azul.png", "pino_verde.png", "pino_amarelo.png",
            "pino_branco.png", "pino_vermelho.png", "pino_preto.png"
    };

    public void setJogo(Jogo jogo) {
        Jogador vencedor = jogo.getVencedor();

        lblVencedor.setText("Vencedor: " + vencedor);
        imgVencedor.setImage(carregarImagem(vencedor.getCor()));
        txtResultado.setText(jogo.resultadoFinal());
    }

    private Image carregarImagem(String cor) {
        Map<String, String> arquivos = new HashMap<>();
        for (int i = 0; i < CORES.length; i++) {
            arquivos.put(CORES[i], IMAGENS[i]);
        }
        String arquivo = arquivos.get(cor);
        return new Image(getClass().getResourceAsStream("/com/example/jogo/image/" + arquivo));
    }

    @FXML
    private void reiniciar() {
        try {
            Parent root = new FXMLLoader(
                    getClass().getResource("/com/example/jogo/TelaInicial.fxml")).load();

            Stage stage = (Stage) lblVencedor.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}