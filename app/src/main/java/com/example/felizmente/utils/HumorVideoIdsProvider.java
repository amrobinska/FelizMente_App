package com.example.felizmente.utils;

import java.util.Random;

public class HumorVideoIdsProvider {
    private static String[] videoIds = {"ECC-76hmXqc","kRsYSVSt3AY","gcW8YeANiLc","iBK3C4T14uQ","TcP6cQy7YTg","qHqDpUpbpJI",
                                        "aRwpDsI-csI","5Yl83kZ4XZo","OmqIymYFbk0","kxtKiZCl7eM","Fs6Qr5xAoRU","WsFNWokbh6M",
                                        "G5LTc19NVDM","z-zlSXvdlws","g5nlhGzJbmI","tiIEy-1QG8M","Yfub-qBfUkk","0DT4i0VsWOw"
                                        };
    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }
}
