package carsharing.util;

/** Консольний журнал у форматі [час] [джерело] подія. Час умовний: кожен запис +1 секунда. */
public class Logger {
    private static int seconds = 0;

    private Logger() {
    }

    public static void log(String source, String event) {
        seconds++;
        System.out.println("[" + time() + "] [" + source + "] " + event);
    }

    /** Поточний умовний час (без зсуву). */
    public static String time() {
        int total = 12 * 3600 + seconds;
        return String.format("%02d:%02d:%02d", total / 3600, (total / 60) % 60, total % 60);
    }
}
