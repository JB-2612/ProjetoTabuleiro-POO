package com.example.jogo;
import java.util.Random;


public class Dado {
    private final Random random = new Random();
    public int rolar(){
        return random.nextInt(6) + 1;
    }
}
