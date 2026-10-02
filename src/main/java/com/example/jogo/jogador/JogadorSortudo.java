package com.example.jogo.jogador;

class JogadorSortudo extends Jogador{
    public JogadorSortudo(String cor) { super(cor); }
    public String getTipo() { return "Sortudo"; }
    protected boolean somaValida(int s) { return s >= 7; }
}
