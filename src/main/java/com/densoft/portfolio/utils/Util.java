package com.densoft.portfolio.utils;

import java.text.Normalizer;
import java.util.UUID;

public class Util {
    private static final int MAX_SLUG_LENGTH = 30;

    public static String generateSlug(String title) {
        final String intermediateResult = Normalizer
                .normalize(title, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^-_a-zA-Z0-9]", "-").replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-").replaceAll("^-", "")
                .replaceAll("-$", "").toLowerCase();
        return intermediateResult.substring(0,
                Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
    }


    public static String generateRandomUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "").substring(0, 10);
    }

}
