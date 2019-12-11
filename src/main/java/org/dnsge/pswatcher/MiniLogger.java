package org.dnsge.pswatcher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple logger that logs to stdout
 */
public class MiniLogger {

    private static final SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd kk:mm:ss");

    /**
     * Get log line prefix date/time
     *
     * @param isErr Whether to write "ERR" or "LOG"
     * @return Date/time prefix
     */
    private static String genPrefix(boolean isErr) {
        String dateString = format.format(new Date());
        return String.format("[%s] [%s]", dateString, isErr ? "ERR" : "LOG");
    }

    /**
     * Log a string
     *
     * @param str String
     */
    static void log(String str) {
        System.out.printf("%s %s\n", genPrefix(false), str);
    }

    /**
     * Log a formatted string
     *
     * @param format String format
     * @param args Format args
     */
    static void log(String format, Object... args) {
        log(String.format(format, args));
    }

    /**
     * Log an error
     *
     * @param str String
     */
    static void err(String str) {
        System.err.printf("%s %s\n", genPrefix(true), str);
    }

    /**
     * Log a Throwable object
     *
     * @param e Throwable object
     */
    static void err(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        e.printStackTrace(printWriter);

        System.err.printf("%s\n", genPrefix(true));
        System.err.println(writer.toString());
    }

}
