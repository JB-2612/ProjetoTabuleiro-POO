package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;
import com.example.jogo.jogador.JogadorAzarado;
import com.example.jogo.jogador.JogadorNormal;
import com.example.jogo.jogador.JogadorSortudo;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class CasaSurpresa extends Casa {

    private static final List<Function<String, Jogador>> CARTAS = List.of(
            JogadorNormal::new,
            JogadorSortudo::new,
            JogadorAzarado::new
    );

    private final Random random = new Random();

    public CasaSurpresa(int numero) {
        super(numero);
    }

    @Override
    public String getRotulo() {
        return "?";
    }

    @Override
    public String getCorFundo() {
        return "#d9c6ff";
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        Jogador novo = CARTAS.get(random.nextInt(CARTAS.size())).apply(jogador.getCor());
        novo.copiarEstadoDe(jogador);
        jogo.substituirJogador(jogador, novo);
        return "Casa surpresa! " + jogador + " tirou uma carta e agora é do tipo " + novo.getTipo() + ".";
    }
}