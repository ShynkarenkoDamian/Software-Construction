package lab7.variant8.loaninterest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/** Допоміжні методи тестів завдання 2. */
final class LoanTestSupport {

    private LoanTestSupport() {
    }

    static LoanApplicant applicant(int score, String income) {
        return new LoanApplicant("APP-" + score, score, new BigDecimal(income), new BigDecimal("10000.00"));
    }

    /** Результат дії разом із тим, що вона надрукувала в System.out. */
    record Captured<T>(T value, String stdout) {
    }

    static <T> Captured<T> captureStdout(Supplier<T> action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            T value = action.get();
            return new Captured<>(value, buffer.toString(StandardCharsets.UTF_8));
        } finally {
            System.setOut(original);
        }
    }
}
