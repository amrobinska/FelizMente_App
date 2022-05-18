package com.example.felizmente.utils;

import java.util.Random;

public class RelaxationVideoIdsProvider {
    private static String[] videoIds = {"13Sm8MzkAQU","uvXgK3kj4CA","WcXK5Iw8yAk","E4vzsi_HtYA","HQ_hpTK3BCI","PeO0wG6eiDE",
                                        "iw8hWPYZjjY","Pt_NLtk6MLY","okG6BqDzpTA","lyO9R4zcrIQ","gAzaeA-ifNE","LBDJ_h-9WfE","5AZmk0rCVnE",
                                        "65RH4B0z6Gc","Vxqxc022vNY","7XSbmL4d294","tADnCEpbPI8","7H7cTSml5zk","SQBNvKBnQnk","E4vzsi_HtYA"};
    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }
}
