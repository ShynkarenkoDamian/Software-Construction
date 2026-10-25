package lab7.variant8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Базовий клас: форматування %.2f залежить від Locale за замовчуванням
 * (в uk_UA було б «12,34»), тому тести фіксують Locale.US і відновлюють його після кожного тесту.
 */
public abstract class FixedLocaleTest {

    private Locale originalLocale;

    @BeforeEach
    protected void fixLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    protected void restoreLocale() {
        Locale.setDefault(originalLocale);
    }

    /** Порівняння грошових сум за значенням, а не за scale (182.4 == 182.40). */
    protected static void assertAmount(String expected, BigDecimal actual) {
        assertEquals(0, new BigDecimal(expected).compareTo(actual),
                "Очікувалось " + expected + ", отримано " + actual);
    }
}
