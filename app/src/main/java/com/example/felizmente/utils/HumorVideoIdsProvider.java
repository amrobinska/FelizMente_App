package com.example.felizmente.utils;

import java.util.Random;

public class HumorVideoIdsProvider {
    private static String[] videoIds = {"UpzDkJ6Ldk4","W-dQVAnFZM8","kRsYSVSt3AY","0wnKZEla99U"};
    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }
}
