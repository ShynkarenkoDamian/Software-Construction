package ua.miskavto.lab15.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Консольний журнал подій у форматі: [час] [джерело] подія. Час умовний (кожна подія +1 с). */
public final class Log {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LocalTime now = LocalTime.of(9, 0, 0);

    private Log() {
    }

    public static void log(String source, String event) {
        System.out.println("[" + now.format(FORMAT) + "] [" + source + "] " + event);
        now = now.plusSeconds(1);
    }

    public static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    public static String money(double amount) {
        return String.format(Locale.ROOT, "%.2f грн", amount);
    }
}
