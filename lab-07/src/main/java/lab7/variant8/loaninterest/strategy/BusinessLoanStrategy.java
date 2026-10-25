package lab7.variant8.loaninterest.strategy;

import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;

/**
 * ДЕМОНСТРАЦІЯ OCP: новий кредитний продукт, доданий БЕЗ зміни жодного рядка
 * в {@code LoanInterestCalculator} та в інших стратегіях.
 *
 * УВАГА: числа нижче ілюстративні (їх немає у вихідному завданні) і придумані
 * лише для демонстрації розширення.
 */
public class BusinessLoanStrategy implements InterestRateStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("0.130");
    private static final BigDecimal DISCOUNT = new BigDecimal("0.010");
    private static final int DISCOUNT_SCORE_FROM = 700;

    @Override
    public String productName() {
        return "BUSINESS_LOAN";
    }

    @Override
    public BigDecimal calculateRate(LoanApplicant applicant) {
        if (applicant.getCreditScore() >= DISCOUNT_SCORE_FROM) {
            return BASE_RATE.subtract(DISCOUNT);
        }
        return BASE_RATE;
    }
}
