package com.example.jogo.ui;

import com.example.jogo.Jogo;
import com.example.jogo.Tabuleiro;
import com.example.jogo.casa.Casa;
import com.example.jogo.jogador.Jogador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaJogoController {

    @FXML private GridPane tabuleiroPane;
    @FXML private Label lblVez, lblDados;
    @FXML private TextField txtCasaDebug;
    @FXML private Button btnJogar;
    @FXML private TextArea txtLog;

    private static final int TAMANHO_CELULA = 95;

    private static final String[] CORES = {"Azul", "Verde", "Amarelo", "Branco", "Vermelho", "Preto"};
    private static final String[] IMAGENS = {
            "pino_azul.png", "pino_verde.png", "pino_amarelo.png",
            "pino_branco.png", "pino_vermelho.png", "pino_preto.png"
    };

    private final Map<String, Image> imagensPorCor = new HashMap<>();

    private Jogo jogo;

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
        carregarImagens();
        jogo.setEscolhedor(this::dialogoEscolha);
        txtCasaDebug.setVisible(jogo.isDebug());
        atualizarTela();
    }

    private void carregarImagens() {
        for (int i = 0; i < CORES.length; i++) {
            Image img = new Image(getClass().getResourceAsStream("/com/example/jogo/image/" + IMAGENS[i]));
            imagensPorCor.put(CORES[i], img);
        }
    }


    @FXML
    private void jogar() {
        Integer casaDebug = null;

        if (jogo.isDebug()) {
            casaDebug = lerCasaDebug();
            if (casaDebug == null) return;
        }

        List<String> mensagens = jogo.jogarTurno(casaDebug);
        for (String m : mensagens) {
            txtLog.appendText(m + "\n");
        }
        txtLog.appendText("Posições: " + jogo.resumoPosicoes() + "\n\n");

        atualizarLabelDados();
        atualizarTela();

        if (jogo.getVencedor() != null) {
            abrirTelaResultado();
        }
    }

    private void atualizarLabelDados() {
        if (jogo.isDebug()) {
            lblDados.setText("Modo debug (sem dados)");
        } else if (jogo.getUltimoDado1() >= 0) {
            lblDados.setText("Últimos dados: " + jogo.getUltimoDado1() + " e " + jogo.getUltimoDado2()
                    + " (soma: " + (jogo.getUltimoDado1() + jogo.getUltimoDado2()) + ")");
        }
    }

    private Integer lerCasaDebug() {
        try {
            int casa = Integer.parseInt(txtCasaDebug.getText().trim());
            if (casa < 0) throw new NumberFormatException();
            return casa;
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Digite um número de casa válido.").showAndWait();
            return null;
        }
    }

    private Jogador dialogoEscolha(Jogador quem, List<Jogador> opcoes) {
        ChoiceDialog<Jogador> dlg = new ChoiceDialog<>(opcoes.get(0), opcoes);
        dlg.setTitle("Casa especial");
        dlg.setHeaderText(quem + ": escolha quem volta para o início");
        return dlg.showAndWait().orElse(opcoes.get(0));
    }

    // ---------- Montagem do tabuleiro em espiral ----------

    private void atualizarTela() {
        lblVez.setText("Vez de: " + jogo.getJogadorDaVez());
        tabuleiroPane.getChildren().clear();

        List<int[]> coordenadas = gerarCoordenadasEspiral(Tabuleiro.FIM + 1);

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (int[] c : coordenadas) {
            minX = Math.min(minX, c[0]);
            minY = Math.min(minY, c[1]);
        }

        for (int i = 0; i <= Tabuleiro.FIM; i++) {
            Casa casa = jogo.getTabuleiro().getCasa(i);
            String seta = calcularSeta(i, coordenadas);
            VBox cel = criarCelula(i, casa, seta);

            int[] coord = coordenadas.get(i);
            int col = coord[0] - minX;
            int row = coord[1] - minY;

            tabuleiroPane.add(cel, col, row);
        }
    }

    /**
     * Gera as coordenadas de uma espiral quadrada, começando no centro (0,0)
     * e crescendo para fora. Depois inverte: índice 0 = ponto mais externo,
     * último índice = centro (0,0) — assim a casa 0 fica na borda e a casa
     * final fica no meio do tabuleiro.
     */
    private List<int[]> gerarCoordenadasEspiral(int total) {
        List<int[]> visitados = new ArrayList<>();
        int x = 0, y = 0;
        visitados.add(new int[]{x, y});

        int[][] direcoes = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}; // direita, baixo, esquerda, cima
        int dirIndex = 0;
        int passo = 1;

        while (visitados.size() < total) {
            for (int rep = 0; rep < 2 && visitados.size() < total; rep++) {
                int dx = direcoes[dirIndex][0];
                int dy = direcoes[dirIndex][1];
                for (int s = 0; s < passo && visitados.size() < total; s++) {
                    x += dx;
                    y += dy;
                    visitados.add(new int[]{x, y});
                }
                dirIndex = (dirIndex + 1) % 4;
            }
            passo++;
        }

        Collections.reverse(visitados);
        return visitados;
    }

    private VBox criarCelula(int indice, Casa casa, String seta) {
        Label num = new Label(String.valueOf(indice));
        num.setStyle("-fx-font-size: 12;");

        ImageView icone = criarIcone(casa.getIcone());

        Label setaLabel = new Label(seta);
        setaLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #555; -fx-font-weight: bold;");

        List<Jogador> presentes = new ArrayList<>();
        for (Jogador j : jogo.getJogadores()) {
            if (j.getPosicao() == indice) {
                presentes.add(j);
            }
        }
        VBox peoes = montarPinos(presentes);

        VBox cel = new VBox(2, num);
        if (icone != null) {
            cel.getChildren().add(icone);
        }
        cel.getChildren().add(setaLabel);
        cel.getChildren().add(peoes);

        if (indice == Tabuleiro.FIM) {
            ImageView imgFinal = new ImageView(
                    new Image(getClass().getResourceAsStream("/com/example/jogo/image/final.png")));
            imgFinal.setFitWidth(20);
            imgFinal.setPreserveRatio(true);
            cel.getChildren().add(imgFinal);
        }

        cel.setAlignment(Pos.CENTER);
        cel.setMinSize(TAMANHO_CELULA, TAMANHO_CELULA);
        cel.setPrefSize(TAMANHO_CELULA, TAMANHO_CELULA);
        cel.setMaxSize(TAMANHO_CELULA, TAMANHO_CELULA);
        cel.setStyle("-fx-border-color:#888; -fx-padding:3; -fx-background-color:" + casa.getCorFundo() + ";");

        return cel;
    }

    private ImageView criarIcone(String nomeArquivo) {
        if (nomeArquivo == null) {
            return null;
        }
        Image img = new Image(getClass().getResourceAsStream("/com/example/jogo/image/" + nomeArquivo));
        ImageView icone = new ImageView(img);
        icone.setFitWidth(18);
        icone.setFitHeight(18);
        icone.setPreserveRatio(true);
        return icone;
    }

    private String calcularSeta(int indice, List<int[]> coordenadas) {
        if (indice == Tabuleiro.FIM) {
            return "🏁";
        }
        int[] atual = coordenadas.get(indice);
        int[] proximo = coordenadas.get(indice + 1);
        int dx = proximo[0] - atual[0];
        int dy = proximo[1] - atual[1];

        if (dx == 1) return "→";
        if (dx == -1) return "←";
        if (dy == 1) return "↓";
        if (dy == -1) return "↑";
        return "";
    }

    private VBox montarPinos(List<Jogador> presentes) {
        VBox container = new VBox(1);
        container.setAlignment(Pos.CENTER);

        if (presentes.isEmpty()) {
            return container;
        }

        int totalLinha1 = (int) Math.ceil(presentes.size() / 2.0);
        List<Jogador> linha1 = presentes.subList(0, Math.min(totalLinha1, presentes.size()));
        List<Jogador> linha2 = presentes.size() > totalLinha1
                ? presentes.subList(totalLinha1, presentes.size())
                : List.of();

        container.getChildren().add(criarLinhaPinos(linha1));
        if (!linha2.isEmpty()) {
            container.getChildren().add(criarLinhaPinos(linha2));
        }

        return container;
    }

    private HBox criarLinhaPinos(List<Jogador> jogadoresNaLinha) {
        HBox linha = new HBox(2);
        linha.setAlignment(Pos.CENTER);
        for (Jogador j : jogadoresNaLinha) {
            ImageView pino = new ImageView(imagensPorCor.get(j.getCor()));
            pino.setFitWidth(18);
            pino.setFitHeight(18);
            pino.setPreserveRatio(true);
            linha.getChildren().add(pino);
        }
        return linha;
    }

    private void abrirTelaResultado() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/jogo/TelaResultado.fxml"));
            Parent root = loader.load();

            TelaResultadoController controller = loader.getController();
            controller.setJogo(jogo);

            Stage stage = (Stage) btnJogar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}