package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

public class CasaNormal extends Casa {

    public CasaNormal(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        return "";
    }
}