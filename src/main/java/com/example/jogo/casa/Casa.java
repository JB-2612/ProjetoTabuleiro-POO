package com.example.jogo.casa;

import com.example.jogo.Jogo;
import com.example.jogo.jogador.Jogador;

public abstract class Casa {

    private final int numero;

    protected Casa(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    /** Nome do arquivo de ícone (em src/main/resources/com/example/jogo/image/), ou null se não tiver ícone. */
    public String getIcone() {
        return null;
    }

    public String getCorFundo() {
        return "white";
    }

    public abstract String aplicarEfeito(Jogo jogo, Jogador jogador);
}