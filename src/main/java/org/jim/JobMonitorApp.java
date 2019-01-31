package org.jim;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobMonitorApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JobMonitorApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
