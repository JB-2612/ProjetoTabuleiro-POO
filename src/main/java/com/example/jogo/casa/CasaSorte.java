package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

public class CasaSorte extends Casa {

    public CasaSorte(int numero) {
        super(numero);
    }

    @Override
    public String getRotulo() {
        return "★";
    }

    @Override
    public String getCorFundo() {
        return "#fff3c4";
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        if (jogador.andaComSorte()) {
            jogador.mover(3);
            return "Casa da sorte! " + jogador + " avança 3 casas, para a casa " + jogador.getPosicao() + ".";
        }
        return "Casa da sorte, mas " + jogador + " é azarado e não avança.";
    }
}