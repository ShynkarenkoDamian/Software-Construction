package carsharing.common;

/**
 * Єдиний спосіб виводу подій у консоль: [час] [джерело] подія.
 * Час умовний: починається з 09:00:00 і збільшується на секунду з кожною подією.
 */
public final class Log {

    private static int seconds = 9 * 3600;

    private Log() {
    }

    public static void log(String source, String event) {
        int s = seconds++;
        System.out.printf("[%02d:%02d:%02d] [%s] %s%n", s / 3600, s / 60 % 60, s % 60, source, event);
    }
}
