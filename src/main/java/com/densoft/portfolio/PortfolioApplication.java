package com.densoft.portfolio;

import com.densoft.portfolio.model.Tag;
import com.densoft.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PortfolioApplication {


    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }


}
