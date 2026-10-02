package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

import java.util.Comparator;

public class CasaMagica extends Casa {

    public CasaMagica(int numero) {
        super(numero);
    }

    @Override
    public String getRotulo() {
        return "✨";
    }

    @Override
    public String getCorFundo() {
        return "#c6e9ff";
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        Jogador ultimo = jogo.getJogadores().stream()
                .min(Comparator.comparingInt(Jogador::getPosicao))
                .orElseThrow();

        if (ultimo == jogador || ultimo.getPosicao() == jogador.getPosicao()) {
            return "Casa mágica! " + jogador + " é o último e não sai do lugar.";
        }

        int posicaoTemp = jogador.getPosicao();
        jogador.setPosicao(ultimo.getPosicao());
        ultimo.setPosicao(posicaoTemp);
        return "Casa mágica! " + jogador + " trocou de lugar com " + ultimo + ".";
    }
}