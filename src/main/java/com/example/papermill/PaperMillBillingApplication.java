package com.example.papermill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Paper Mill Billing application.  Running the main
 * method will start an embedded Tomcat server and expose the REST API
 * defined in the controller package.
 */
@SpringBootApplication
public class PaperMillBillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperMillBillingApplication.class, args);
    }
}