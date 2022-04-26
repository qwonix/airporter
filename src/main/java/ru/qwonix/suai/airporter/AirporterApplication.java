package ru.qwonix.suai.airporter;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirporterApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AirporterApplication.class, args);
        Application.launch(JavaFxApplication.class, args);
    }

}
