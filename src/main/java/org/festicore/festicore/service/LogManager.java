package org.festicore.festicore.service;

import java.util.logging.*;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class LogManager {
    private static Logger logger = Logger.getLogger("Festicore");
    
    public static void setup() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            props.load(fis);
            props.load(fis);
            
            String fileName = props.getProperty("log.filename", "festicore_default.log");
            String levelStr = props.getProperty("log.level", "INFO");
            Level level = Level.parse(levelStr.toUpperCase());

            // Create a FileHandler (append mode: true)
            FileHandler fileHandler = new FileHandler(fileName, true);
            
            // Format: Simple text instead of XML
            fileHandler.setFormatter(new SimpleFormatter());
            // Ensure handler logs at configured level
            fileHandler.setLevel(level);
            
            // Also add a console handler using same level/format
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(level);
            consoleHandler.setFormatter(new SimpleFormatter());
            
            // Configure the logger
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
            logger.setLevel(level);
            
            // Log the successful initialization
            logger.info("Logging system initialized. Level: " + level);
        } catch (IOException | IllegalArgumentException e) {
            // Fallback if the config file is missing or corrupted
            System.err.println("Could not setup logger, using console default: " + e.getMessage());
        }
    }

    /**
     * Provides access to the configured logger.
     * @return The static Logger instance
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Convenience logging helpers so other classes can call LogManager.log(...)
     */
    public static void log(Level level, String msg) {
        logger.log(level, msg);
    }

    public static void log(Level level, String msg, Throwable t) {
        logger.log(level, msg, t);
    }
}

