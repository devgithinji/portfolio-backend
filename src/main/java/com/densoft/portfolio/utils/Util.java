package com.densoft.portfolio.utils;

import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.UUID;

@Component
public class Util {

    @Autowired
    private TagRepository tagRepository;

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

    public Tag generateTag(String matchTag) {
        return tagRepository.findAll().stream().filter(tag -> tag.getName().equalsIgnoreCase(matchTag)).findFirst().get();
    }

}
