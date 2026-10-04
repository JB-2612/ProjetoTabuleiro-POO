package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

import java.util.ArrayList;
import java.util.List;

public class CasaVoltaInicio extends Casa {

    public CasaVoltaInicio(int numero) {
        super(numero);
    }

    @Override
    public String getIcone() {
        return "volta.png";
    }

    @Override
    public String getCorFundo() {
        return "#ffd9a0";
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        List<Jogador> outros = new ArrayList<>(jogo.getJogadores());
        outros.remove(jogador);

        Jogador escolhido = jogo.escolherJogador(jogador, outros);
        if (escolhido == null) {
            return jogador + " não escolheu ninguém.";
        }

        escolhido.setPosicao(0);
        return jogador + " mandou " + escolhido + " de volta para o início!";
    }
}