package com.example.jogo.jogador;

class JogadorAzarado extends Jogador {
    public JogadorAzarado(String cor) { super(cor); }
    public String getTipo() { return "Azarado"; }
    protected boolean somaValida(int s) { return s <= 6; }
    @Override public boolean andaComSorte() { return false; }
}
