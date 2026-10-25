package lab7.variant8.loaninterest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static lab7.variant8.loaninterest.LoanTestSupport.applicant;
import static lab7.variant8.loaninterest.LoanTestSupport.captureStdout;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoanInterestCalculatorTest {

    private final LoanInterestCalculator calculator = new LoanInterestCalculator();

    /** Стратегія, оголошена прямо в тесті, — калькулятор про неї нічого не знає на етапі компіляції. */
    private static InterestRateStrategy fixedRate(String name, String rate) {
        return new InterestRateStrategy() {
            @Override
            public String productName() {
                return name;
            }

            @Override
            public BigDecimal calculateRate(LoanApplicant applicant) {
                return new BigDecimal(rate);
            }
        };
    }

    @Test
    @DisplayName("OCP: невідомий калькулятору продукт працює без змін у калькуляторі")
    void newProductWorksWithoutModifyingCalculator() {
        BigDecimal rate = calculator.calculateInterestRate(applicant(700, "1000.00"), fixedRate("LEASING", "0.0925"));

        assertEquals("0.0925", rate.toPlainString());
    }

    @Test
    @DisplayName("Остаточне округлення до 4 знаків (HALF_UP) виконує калькулятор")
    void roundsToFourDecimalPlaces() {
        assertEquals("0.1235",
                calculator.calculateInterestRate(applicant(700, "1000.00"), fixedRate("X", "0.12345")).toPlainString());
        assertEquals("0.1234",
                calculator.calculateInterestRate(applicant(700, "1000.00"), fixedRate("X", "0.12344")).toPlainString());
    }

    @Test
    @DisplayName("Формат журналу збережено: [LOAN] Заявка від … -> Ставка: …%")
    void logFormatIsPreserved() {
        LoanTestSupport.Captured<BigDecimal> result = captureStdout(
                () -> calculator.calculateInterestRate(applicant(720, "1000.00"), fixedRate("MORTGAGE", "0.075")));

        assertEquals("[LOAN] Заявка від APP-720 (Скоринг: 720): Продукт=MORTGAGE -> Ставка: 7.5000%"
                + System.lineSeparator(), result.stdout());
    }

    @Test
    @DisplayName("Guard clauses: заявник і стратегія обов'язкові")
    void nullArgumentsFailFast() {
        assertThrows(NullPointerException.class,
                () -> calculator.calculateInterestRate(null, fixedRate("X", "0.1")));
        assertThrows(NullPointerException.class,
                () -> calculator.calculateInterestRate(applicant(700, "1000.00"), null));
    }
}
