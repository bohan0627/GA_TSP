package edu.neu.info6205.finalproj.num09.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Log Utilities.
 */


public class LogUtil {

    // Regular Date Format
    public static final String DATE_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    // Date Format without Mark
    public static final String DATE_PATTERN_NOMARK = "yyyyMMddHHmmss";

    /**
     * Set level of log
     * @param log
     * @param level
     */
    public static void setLogLevel(Logger log, Level level) {
        log.setLevel(level);
    }

    /**
     * Console Handler
     * @param log
     * @param level
     */
    public static void addConsoleHandler(Logger log, Level level) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(level);
        log.addHandler(consoleHandler);
    }

    /**
     * File Handler
     * @param log
     * @param level
     * @param filePath
     */
    public static void addFileHandler(Logger log, Level level, String filePath) {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(filePath);

            fileHandler.setLevel(level);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {

                // file output format
                    return "[ " + getCurrentDateStr(DATE_PATTERN_FULL) + " - Level:"
                            + record.getLevel().getName().substring(0, 1) + " ]-" + "[" + record.getSourceClassName()
                            + " -> " + record.getSourceMethodName() + "()] " + record.getMessage() + "\n";
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // addhandler
        log.addHandler(fileHandler);
    }

    /**
     * get current Date time
     *
     * @return String
     */
    public static String getCurrentDateStr(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}