package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TariffCalculatorTest extends FixedLocaleTest {

    private final TariffCalculator calculator = new TariffCalculator();

    @Test
    @DisplayName("Нуль кВт·год коштує нуль")
    void zeroConsumption() {
        assertAmount("0.00", calculator.calculateCost(0));
    }

    @Test
    @DisplayName("Перший ступінь: до 100 кВт·год включно по 2.64")
    void firstTierOnly() {
        assertAmount("2.64", calculator.calculateCost(1));
        assertAmount("264.00", calculator.calculateCost(100));
    }

    @Test
    @DisplayName("Другий ступінь: понад 100 кВт·год по 4.32")
    void secondTierStartsAfterHundred() {
        assertAmount("268.32", calculator.calculateCost(101));
        assertAmount("912.00", calculator.calculateCost(250));
        assertAmount("21432.00", calculator.calculateCost(5000));
    }

    @Test
    @DisplayName("Від'ємне споживання — fail-fast")
    void negativeConsumptionIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateCost(-1));
    }
}
