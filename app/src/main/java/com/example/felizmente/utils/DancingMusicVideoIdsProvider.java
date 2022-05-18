package com.example.felizmente.utils;

import java.util.Random;

public class DancingMusicVideoIdsProvider {
    private static String[] videoIds =  {"LkgyV_tTQfQ","JbS7C9056cQ","m_aqPhxRcKY","LXI3UbqYwn4","8TO9cMLsUCw",
                                        "yG7MPEQm1-w","LUtVPJ_QMeE","Zdqm1Imkyi4","v685hHVCvYw","id0MYEtcDtY",
                                        "mZzZsg1XtrE","kmWff7h0hRo","L2SG1P86JCQ","1aJdOHHRIgg","qmbx4_TQbkA","tlEZmMgfviU"};

    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }

}
