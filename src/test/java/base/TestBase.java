package base;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * All test classes should extend this class. It will provide any generic setup and configuration
 * required by the tests.
 */
public class TestBase {

  protected static SimpleDateFormat logfileSDF = new SimpleDateFormat("yyyyMMdd-HH-mm");

  @BeforeClass
  public static void setup() {
    setupLoggers();
  }

  /**
   * This will configure the loggers (both log4j and the loggers used by C3P0). The loggers should
   * create a new log file for each test run. The log file will be overwritten each run, so you will
   * want to save a copy if it has anything of interest in it.
   */
  private static void setupLoggers() {
    // creates pattern layout
    PatternLayout layout = new PatternLayout();
    String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
    layout.setConversionPattern(conversionPattern);

    // creates console appender
    ConsoleAppender consoleAppender = new ConsoleAppender();
    consoleAppender.setLayout(layout);
    consoleAppender.activateOptions();

    // creates file appender
    FileAppender fileAppender = new FileAppender();
    fileAppender.setFile("tourny_test_" + logfileSDF.format(new Date()) + ".log");
    fileAppender.setLayout(layout);
    fileAppender.activateOptions();
    fileAppender.setAppend(false);

    // configures the root logger
    Logger rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.DEBUG);
    rootLogger.addAppender(consoleAppender);
    rootLogger.addAppender(fileAppender);

    // Configure the logging for C3P0
    Logger.getLogger("com.mchange.v2.c3p0").setLevel(Level.WARN);
    Logger.getLogger("com.mchange.v2").setLevel(Level.WARN);

    Properties p = new Properties(System.getProperties());
    p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.log4j.Log4jMLog");
    p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "INFO");
    System.setProperties(p);
  }
}
