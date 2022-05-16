package com.example.felizmente.utils;

import java.util.Random;

public class NostalgicMusicVideoIdsProvider {
    private static String[] videoIds = {"D4ptueTp_m8","5xzEnGpSt6A","rFIG0KiTO4A","qBse_vYeiiQ","RT0AOInt5XM","TjFPo27c5po","NwucT5lB6Gc","CYKTiXrTENM",
                                        "WwWHyllwePU","MLrhPIQW9Z4","cmif806JzjY","Jy3jC8u63Cg","kkx76rDk980","fOHrY0jV6Hg","zVVPgQeLQds","ShGH_3DFmk4",
                                        "xKolfXCeoo4","6yAohpXRb6w","ZqoISjMiKuQ","lmdodWvt65w","3wg2a3s--aY","7R9NxOacfj0","f9Cb8qv9VV0","aOMK43bqh8Q",
                                        "XDK5JHhhM0I","xh9EFTp3gNs","Ng2TgYUc884","N5Rxm8o9FvY","9xjDwp-wqE4","nukOXwlQEMM"};
    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];

    }
}