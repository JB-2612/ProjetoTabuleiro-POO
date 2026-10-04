package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

public class CasaPerdeVez extends Casa {

    public CasaPerdeVez(int numero) {
        super(numero);
    }

    @Override
    public String getIcone() {
        return "para.png";
    }

    @Override
    public String getCorFundo() {
        return "#ffcccc";
    }

    @Override
    public String aplicarEfeito(Jogo jogo, Jogador jogador) {
        jogador.setPerdeProximaRodada(true);
        return jogador + " caiu na casa " + getNumero() + " e não joga na próxima rodada!";
    }
}