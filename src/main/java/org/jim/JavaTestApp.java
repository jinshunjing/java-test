package org.jim;

import org.jim.nio.server.MyNIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaTestApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JavaTestApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyNIOServer nioServer = new MyNIOServer();
        nioServer.init();
        nioServer.listen();
        Thread.sleep(5_000L);
    }

}
