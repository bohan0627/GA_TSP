package edu.neu.info6205.finalprojectNum09.log;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Definition of Global log variables
 */
public class LogGlobe {

    // the name of Global log
    public static final String LOG_NAME = "Global";

    // log folder
    public static final String LOG_FOLDER = System.getProperty("user.dir") ;   //Get the path of Project directory.

    // log filepath
    private static String log_filepath;


    private static Logger globalLog;

    static {

    // initialize filepath，Log_+time+.log
        log_filepath = LOG_FOLDER + File.separator + "Log_" + LogUtil.getCurrentDateStr(LogUtil.DATE_PATTERN_NOMARK)
                + ".log";
        globalLog = initGlobalLog();
    }

    /**
     * Initialize Global log
     *
     * @return
     */
    public static Logger initGlobalLog() {

        Logger log = Logger.getLogger(LOG_NAME);
        log.setLevel(Level.ALL);
        LogUtil.addConsoleHandler(log, Level.INFO);
        LogUtil.addFileHandler(log, Level.INFO, log_filepath);

        // avoid duplicate outputs in console.
        log.setUseParentHandlers(false);

        return log;
    }

    public static Logger getGlobalLog() {
        return globalLog;
    }

}