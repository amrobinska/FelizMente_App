package com.example.felizmente.utils;

import java.util.Random;

public class NostalgicVideosIdsProvider {
        private static String[] videoIds = {"R3txROr2URM","i7il2bShGGQ","pz6TeRhHIUo","5Jf2sXiOy00","9zEeNIQLYuk","VHZSCs6NygA","Yz-Uh5rb9Tw",
                                            "YxCRj9VZYtk","IYItPXuGY5M","rEXlUBj5OIs"};
        private static Random random = new Random();

        public static String getNextVideoId() {
            return videoIds[random.nextInt(videoIds.length)];
        }
}
