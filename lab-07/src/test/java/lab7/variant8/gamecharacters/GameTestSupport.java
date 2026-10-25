package lab7.variant8.gamecharacters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/** Допоміжні методи тестів завдання 4. */
final class GameTestSupport {

    private GameTestSupport() {
    }

    /** Те, що дія надрукувала у переданий їй потік (для нових класів, які приймають PrintStream). */
    static String output(Consumer<PrintStream> action) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        action.accept(stream);
        return buffer.toString(StandardCharsets.UTF_8);
    }

    /** Те, що дія надрукувала в System.out (для вихідного коду, який пише в консоль напряму). */
    static String systemOut(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            action.run();
            return buffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(original);
        }
    }
}
