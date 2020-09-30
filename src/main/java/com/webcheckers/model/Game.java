package com.webcheckers.model;

import java.util.Random;

public class Game {

    public static String generateRandomGameID() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
            builder.append(random.nextInt(9));
        }
        return builder.toString();
    }
}
