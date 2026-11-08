package lab10.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Спільний консольний журнал подій у форматі {@code [час] [джерело] подія}.
 * <p>
 * Час умовний: починається о 09:00:00 і збільшується на 1 с після кожного запису.
 * <p>
 * Це звичайний утилітний клас зі статичними методами (не Singleton).
 */
public final class ConsoleLogger {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LocalTime simulatedNow = LocalTime.of(9, 0, 0);

    private ConsoleLogger() {
    }

    public static synchronized void log(String source, String event) {
        System.out.printf("[%s] [%s] %s%n", simulatedNow.format(FORMAT), source, event);
        simulatedNow = simulatedNow.plusSeconds(1);
    }

    /** Заголовок розділу демонстрації (не є подією, тому без часу). */
    public static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
