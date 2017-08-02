package app.run;

import config.ServerConfig;
import controller.MainController;
import org.springframework.boot.SpringApplication;

/**
 * Created by Fresher on 27/07/2017.
 */
public class Main {
    public static void main(String[] args) {
        ServerConfig.initConfig();
        SpringApplication.run(MainController.class, args);
    }
}
