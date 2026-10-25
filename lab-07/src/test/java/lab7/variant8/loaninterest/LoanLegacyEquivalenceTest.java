package lab7.variant8.loaninterest;

import lab7.variant8.loaninterest.legacy.LegacyLoanInterestCalculator;
import lab7.variant8.loaninterest.legacy.LegacyLoanInterestCalculator.LoanType;
import lab7.variant8.loaninterest.strategy.AutoLoanStrategy;
import lab7.variant8.loaninterest.strategy.ConsumerLoanStrategy;
import lab7.variant8.loaninterest.strategy.MortgageStrategy;
import lab7.variant8.loaninterest.strategy.StudentLoanStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static lab7.variant8.loaninterest.LoanTestSupport.applicant;
import static lab7.variant8.loaninterest.LoanTestSupport.captureStdout;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Characterization-тест: нова стратегія для кожного значення enum повинна давати
 * ТУ САМУ ставку (включно з scale) та ТОЙ САМИЙ рядок журналу, що й вихідний switch.
 */
class LoanLegacyEquivalenceTest {

    private static final List<Integer> SCORES = List.of(300, 599, 600, 649, 650, 699, 700, 750, 751, 850);
    private static final List<String> INCOMES = List.of("0.00", "2999.99", "3000.00", "10000.00");

    private static InterestRateStrategy strategyFor(LoanType type) {
        return switch (type) {
            case MORTGAGE -> new MortgageStrategy();
            case CONSUMER_LOAN -> new ConsumerLoanStrategy();
            case AUTO_LOAN -> new AutoLoanStrategy();
            case STUDENT_LOAN -> new StudentLoanStrategy();
        };
    }

    @Test
    @DisplayName("Ставка і журнал збігаються з вихідним switch для всіх продуктів і межових значень")
    void sameRateAndLogForAllProducts() {
        LegacyLoanInterestCalculator legacy = new LegacyLoanInterestCalculator();
        LoanInterestCalculator refactored = new LoanInterestCalculator();

        for (LoanType type : LoanType.values()) {
            for (int score : SCORES) {
                for (String income : INCOMES) {
                    LoanApplicant applicant = applicant(score, income);
                    String label = type + ", скоринг " + score + ", дохід " + income;

                    LoanTestSupport.Captured<BigDecimal> expected =
                            captureStdout(() -> legacy.calculateInterestRate(applicant, type));
                    LoanTestSupport.Captured<BigDecimal> actual =
                            captureStdout(() -> refactored.calculateInterestRate(applicant, strategyFor(type)));

                    assertEquals(expected.value(), actual.value(), "Ставка: " + label);
                    assertEquals(expected.stdout(), actual.stdout(), "Журнал: " + label);
                }
            }
        }
    }
}
