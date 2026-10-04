package com.example.jogo;

import com.example.jogo.casa.Casa;
import com.example.jogo.jogador.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Jogo {

    private final Tabuleiro tabuleiro = new Tabuleiro();
    private final List<Jogador> jogadores;
    private final Dado d1 = new Dado();
    private final Dado d2 = new Dado();
    private int ultimoDado1 = -1;
    private int ultimoDado2 = -1;

    private int vez = 0;
    private final boolean debug;
    private Jogador vencedor;

    private BiFunction<Jogador, List<Jogador>, Jogador> escolhedor = (quem, opcoes) -> opcoes.get(0);

    public Jogo(List<Jogador> jogadores, boolean debug) {
        if (jogadores.size() < 2 || jogadores.size() > 6) {
            throw new IllegalArgumentException("O jogo precisa de 2 a 6 jogadores.");
        }
        if (jogadores.stream().map(Jogador::getTipo).distinct().count() < 2) {
            throw new IllegalArgumentException("É preciso ter pelo menos dois tipos diferentes de jogador.");
        }
        this.jogadores = new ArrayList<>(jogadores);
        this.debug = debug;
    }

    public void setEscolhedor(BiFunction<Jogador, List<Jogador>, Jogador> escolhedor) {
        this.escolhedor = escolhedor;
    }

    public Jogador escolherJogador(Jogador quemEscolhe, List<Jogador> opcoes) {
        return escolhedor.apply(quemEscolhe, opcoes);
    }

    public void substituirJogador(Jogador antigo, Jogador novo) {
        jogadores.set(jogadores.indexOf(antigo), novo);
    }

    public List<String> jogarTurno(Integer casaDebug) {
        List<String> mensagens = new ArrayList<>();
        Jogador jogador = jogadores.get(vez);

        if (jogador.isPerdeProximaRodada()) {
            jogador.setPerdeProximaRodada(false);
            mensagens.add(jogador + " está sem jogar nesta rodada.");
            avancarVez();
            return mensagens;
        }

        jogador.incrementarJogadas();
        boolean jogaDeNovo = false;

        if (debug) {
            jogador.setPosicao(casaDebug);
            mensagens.add("[DEBUG] " + jogador + " vai direto para a casa " + casaDebug + ".");
        } else {
        int[] dados = jogador.lancarDados(d1, d2);
        ultimoDado1 = dados[0];
        ultimoDado2 = dados[1];
        mensagens.add(jogador + " (" + jogador.getTipo() + ") tirou " + dados[0]
                + " e " + dados[1] + " = " + (dados[0] + dados[1]) + ".");
        jogador.mover(dados[0] + dados[1]);
        jogaDeNovo = dados[0] == dados[1];
        }

        if (jogador.getPosicao() < Tabuleiro.FIM) {
            String msg = tabuleiro.getCasa(jogador.getPosicao()).aplicarEfeito(this, jogador);
            if (!msg.isEmpty()) mensagens.add(msg);
        }

        jogador = jogadores.get(vez);

        if (jogador.getPosicao() >= Tabuleiro.FIM) {
            vencedor = jogador;
            mensagens.add(jogador + " chegou à casa 40 e venceu!");
            return mensagens;
        }

        if (jogaDeNovo && !jogador.isPerdeProximaRodada()) {
            mensagens.add("Dados iguais! " + jogador + " joga novamente.");
        } else {
            avancarVez();
        }

        return mensagens;
    }

    private void avancarVez() {
        vez = (vez + 1) % jogadores.size();
    }

    public String resumoPosicoes() {
        StringBuilder sb = new StringBuilder();
        for (Jogador j : jogadores) {
            sb.append(j).append(" na casa ").append(j.getPosicao()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public String resultadoFinal() {
        StringBuilder sb = new StringBuilder("Vencedor: " + vencedor + "\n\n");
        for (Jogador j : jogadores) {
            sb.append(j).append(" – casa ").append(j.getPosicao())
                    .append(", ").append(j.getJogadas()).append(" jogadas\n");
        }
        return sb.toString();
    }

    public Jogador getJogadorDaVez() { return jogadores.get(vez); }
    public List<Jogador> getJogadores() { return jogadores; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public Jogador getVencedor() { return vencedor; }
    public boolean isDebug() { return debug; }
    public int getUltimoDado1() { return ultimoDado1; }
    public int getUltimoDado2() { return ultimoDado2; }
}