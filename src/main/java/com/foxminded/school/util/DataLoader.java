package com.foxminded.school.util;

import com.foxminded.school.service.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private DataGeneratorService dataGeneratorService;

    @Autowired
    public DataLoader(DataGeneratorService dataGeneratorService) {
        this.dataGeneratorService = dataGeneratorService;
    }

    @Override
    public void run(ApplicationArguments args) {
        dataGeneratorService.insertTestDataInDatabase();
    }
}
