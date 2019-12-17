package net.AspectNetwork.bditt.API.utils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GameLogger {

  // Global fields
  // Private global fields
  private static final Formatter DFormatter = new Formatter() {
    public String format(LogRecord rec) {
      return '['
          + (new SimpleDateFormat("HH:mm:ss ")).format(new Date())
          + rec.getLevel()
          + ']'
          + ':'
          + ' '
          + formatMessage(rec)
          + '\n';
    }
  };
  // End of Global Fields
  // Class base fields
  private final Logger LOGGER;
  private boolean DEBUG = false;
  private boolean HAS_DEFAULT_LOG_FILES = false;
  //

  // Constructor
  public GameLogger() {
    this(false);
  }

  public GameLogger(boolean useParentHandlers) {
    LOGGER = Logger.getLogger(this.getClass().getName() + (new Date()).getTime());
    LOGGER.setUseParentHandlers(useParentHandlers);
  }

  public boolean hasDefaultLogFiles() {
    return HAS_DEFAULT_LOG_FILES;
  }

  public void addLogFile(File file) {
    addLogFile(file, false);
  }

  public void addLogFile(File file, boolean reset) {
    addLogFile(file, reset, getFormatter());
  }

  public void addLogFile(File file, boolean reset, Formatter formatter) {
    if (file == null) return;

    try {
      if (file.getParentFile() != null) {
        if (!file.getParentFile().exists()) {
          if (!file.getParentFile().mkdirs())
            throw new IOException();
        }
      }
      if (!file.exists() || reset) {
        if (file.createNewFile()) throw new IOException();
      }
      addHandler(new FileHandler(file.getPath()), formatter);
    } catch (IOException e) {
      error("Could't bind log file to logger!", e);
    }
  }

  public void bindConsoleFromLogger(GameLogger logger) {
    if (logger == null) return;
    for (Handler h : logger.getLogger().getHandlers()) {
      if (h instanceof ConsoleHandler) {
        addHandler(h);
      }
    }
  }

  public Logger getLogger() {
    return LOGGER;
  }

  public void addHandler(Handler handler) {
    addHandler(handler, getFormatter());
  }

  public void addHandler(Handler handler, Formatter formatter) {
    handler.setFormatter(formatter);
    LOGGER.addHandler(handler);
  }

  public static Formatter getFormatter() {
    return DFormatter;
  }

  public void fine(String message) {
    log(Level.FINE, message);
  }

  public void log(Level level, String message) {
    LOGGER.log(level, message);
  }

  public void finer(String message) {
    log(Level.FINER, message);
  }

  public void finest(String message) {
    log(Level.FINEST, message);
  }

  public void severe(String message) {
    log(Level.SEVERE, message);
  }

  public void error(String message, Exception e) {
    severe(message + "\n" + e.getMessage());
  }

  public void debug(String message) {
    if (DEBUG) info("[DEBUG] " + message);
  }

  public void info(String message) {
    log(Level.INFO, message);
  }

  public void printException(Exception e) {
    warning(e.getLocalizedMessage());
    warning(e.getMessage());
    e.printStackTrace();
  }

  public void warning(String message) {
    log(Level.WARNING, message);
  }

  public void createDefaultLogFiles(File folder) {
    if (!HAS_DEFAULT_LOG_FILES) {
      File container = new File(folder, "/log/");
      File logFile = new File(
          container,
          (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date()) + ".log"
      );
      File latestLogFile = new File(container, "latest.log");
      addLogFile(logFile);
      addLogFile(latestLogFile, true);

      HAS_DEFAULT_LOG_FILES = true;
    }
  }

  public void closeActiveLogFiles() {
    for (Handler h : getLogger().getHandlers()) {
      if (h instanceof FileHandler) {
        h.close();
      }
    }
    HAS_DEFAULT_LOG_FILES = false;
  }

  // Getters
  // Getters and Setters
  public boolean isDebugging() {
    return DEBUG;
  }

  public void setDebugging(boolean debug) {
    info("Debugging set to " + debug);
    DEBUG = debug;
  }
}