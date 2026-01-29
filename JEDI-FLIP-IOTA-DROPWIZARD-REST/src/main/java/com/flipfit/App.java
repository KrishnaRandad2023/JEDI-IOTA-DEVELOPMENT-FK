package com.flipfit;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.flipfit.rest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class for the FlipFit Dropwizard REST API.
 * This class initializes the application, registers resources, and starts the
 * server.
 * 
 * @author team IOTA
 */
public class App extends Application<FlipFitConfiguration> {

    /**
     * Default constructor for App.
     */
    public App() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * Entry point for the FlipFit application.
     * 
     * @param args command line arguments
     * @throws Exception if application fails to start
     */
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    /**
     * Returns the name of the application.
     * 
     * @return the application name
     */
    @Override
    public String getName() {
        return "FlipFit-Application";
    }

    /**
     * Initializes the application bootstrap process.
     * 
     * @param bootstrap the application bootstrap
     */
    @Override
    public void initialize(Bootstrap<FlipFitConfiguration> bootstrap) {
        // initialization logic if any
    }

    /**
     * Runs the application by registering resources and environment settings.
     * 
     * @param configuration the application configuration
     * @param environment   the environment in which the application runs
     */
    @Override
    public void run(FlipFitConfiguration configuration, Environment environment) {
        LOGGER.info("Registering Resources");

        // Register Resources
        environment.jersey().register(new AuthController());
        environment.jersey().register(new AdminController());
        environment.jersey().register(new GymCustomerController());
        environment.jersey().register(new GymOwnerController());

        // Register Exception Mappers if needed
    }
}
