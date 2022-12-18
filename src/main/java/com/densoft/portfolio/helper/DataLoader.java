package com.densoft.portfolio.helper;

import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
//        tagRepository.deleteAll();;
        //add tags
        Tag tagOne = new Tag("Java");
        Tag tagTwo = new Tag("Hibernate");
        Tag tagThree = new Tag("Spring Boot");
        Tag tagFour = new Tag("Microservices");
        Tag tagFive = new Tag("Spring Security");
        Tag tagSix = new Tag("JavaScript");
//        tagRepository.saveAll(List.of(tagOne, tagTwo, tagThree, tagFour, tagFive, tagSix));
    }
}
