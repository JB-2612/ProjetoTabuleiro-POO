package com.example.jogo;

import com.example.jogo.casa.Casa;
import com.example.jogo.casa.CasaMagica;
import com.example.jogo.casa.CasaNormal;
import com.example.jogo.casa.CasaPerdeVez;
import com.example.jogo.casa.CasaSorte;
import com.example.jogo.casa.CasaSurpresa;
import com.example.jogo.casa.CasaVoltaInicio;

public class Tabuleiro {

    public static final int FIM = 40;

    private final Casa[] casas = new Casa[FIM + 1];

    public Tabuleiro() {
        for (int i = 0; i <= FIM; i++) {
            casas[i] = new CasaNormal(i);
        }

        for (int n : new int[]{10, 25, 38}) {
            casas[n] = new CasaPerdeVez(n);
        }

        casas[13] = new CasaSurpresa(13);

        for (int n : new int[]{5, 15, 30}) {
            casas[n] = new CasaSorte(n);
        }

        for (int n : new int[]{17, 27}) {
            casas[n] = new CasaVoltaInicio(n);
        }

        for (int n : new int[]{20, 35}) {
            casas[n] = new CasaMagica(n);
        }
    }

    public Casa getCasa(int posicao) {
        return casas[Math.min(posicao, FIM)];
    }
}