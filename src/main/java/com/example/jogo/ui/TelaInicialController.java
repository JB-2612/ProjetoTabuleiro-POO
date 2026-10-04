package com.example.jogo.ui;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TelaInicialController {

    @FXML private VBox boxSelecao;
    @FXML private CheckBox chkDebug;
    @FXML private Label lblErro;

    private static final String[] CORES = {"Azul", "Verde", "Amarelo", "Branco", "Vermelho", "Preto"};
    private static final String[] IMAGENS = {
            "pino_azul.png", "pino_verde.png", "pino_amarelo.png",
            "pino_branco.png", "pino_vermelho.png", "pino_preto.png"
    };

    private final List<ComboBox<String>> combos = new ArrayList<>();

    @FXML
    public void initialize() {
        for (int i = 0; i < CORES.length; i++) {
            boxSelecao.getChildren().add(criarLinha(CORES[i], IMAGENS[i]));
        }
    }

    private HBox criarLinha(String cor, String arquivoImagem) {
        ImageView icone = new ImageView(
                new Image(getClass().getResourceAsStream("/com/example/jogo/image/" + arquivoImagem)));
        icone.setFitWidth(28);
        icone.setFitHeight(28);
        icone.setPreserveRatio(true);

        Label nome = new Label(cor);
        nome.setPrefWidth(80);

        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll("Nenhum", "Normal", "Sortudo", "Azarado");
        combo.setValue("Nenhum");
        combo.setPrefWidth(140);
        combos.add(combo);

        HBox linha = new HBox(12, icone, nome, combo);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setMaxWidth(320);
        return linha;
    }

    @FXML
    private void iniciar() {
        List<Jogador> jogadores = montarJogadores();

        Jogo jogo;
        try {
            jogo = new Jogo(jogadores, chkDebug.isSelected());
        } catch (IllegalArgumentException e) {
            lblErro.setText(e.getMessage());
            return;
        }

        lblErro.setText("");
        abrirTelaJogo(jogo);
    }

    private List<Jogador> montarJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        for (int i = 0; i < CORES.length; i++) {
            switch (combos.get(i).getValue()) {
                case "Normal"  -> jogadores.add(new JogadorNormal(CORES[i]));
                case "Sortudo" -> jogadores.add(new JogadorSortudo(CORES[i]));
                case "Azarado" -> jogadores.add(new JogadorAzarado(CORES[i]));
                default -> { }
            }
        }
        return jogadores;
    }

    private void abrirTelaJogo(Jogo jogo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/jogo/TelaJogo.fxml"));
            Parent root = loader.load();

            TelaJogoController controller = loader.getController();
            controller.setJogo(jogo);

            Stage stage = (Stage) boxSelecao.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            lblErro.setText("Erro ao abrir o jogo: " + e.getMessage());
        }
    }
}