package com.example.jogo.jogador;

public class JogadorNormal extends Jogador{
    public JogadorNormal(String color){
        super (color);
    }
    public String getTipo(){
        return "Normal";
    }
    protected boolean somaValida(int s){
        return true;
    }
}
