package com.example.jogo.jogador;

import com.example.jogo.Dado;

public abstract class Jogador {
    private final String cor;
    private int posicao = 0;
    private int jogadas = 0;
    private boolean perdeProximaRodada = false;

    protected Jogador(String cor) { this.cor = cor; }

    public abstract String getTipo();
    protected abstract boolean somaValida(int soma);

    public boolean andaComSorte() { return true; }

    public int[] lancarDados(Dado d1, Dado d2) {
        int a, b;
        do { a = d1.rolar(); b = d2.rolar(); } while (!somaValida(a + b));
        return new int[]{a, b};
    }

    public void copiarEstadoDe(Jogador outro) {
        this.posicao = outro.posicao;
        this.jogadas = outro.jogadas;
        this.perdeProximaRodada = outro.perdeProximaRodada;
    }

    public void mover(int casas) { posicao += casas; }
    public void incrementarJogadas() { jogadas++; }

    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }
    public void setPosicao(int p) { posicao = p; }
    public int getJogadas() { return jogadas; }
    public boolean isPerdeProximaRodada() { return perdeProximaRodada; }
    public void setPerdeProximaRodada(boolean b) { perdeProximaRodada = b; }

    @Override public String toString() { return cor; }
}