package lab7.variant8.loaninterest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Контекст патерну Strategy. Не містить розгалужень за типом продукту:
 * поведінку визначає переданий {@link InterestRateStrategy} (поліморфна диспетчеризація).
 * Закритий для модифікації, відкритий для розширення новими стратегіями.
 */
public class LoanInterestCalculator {

    private static final int RATE_SCALE = 4;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public BigDecimal calculateInterestRate(LoanApplicant applicant, InterestRateStrategy strategy) {
        Objects.requireNonNull(applicant, "applicant");
        Objects.requireNonNull(strategy, "strategy");

        BigDecimal rate = strategy.calculateRate(applicant).setScale(RATE_SCALE, RoundingMode.HALF_UP);

        System.out.println("[LOAN] Заявка від " + applicant.getApplicantId()
                + " (Скоринг: " + applicant.getCreditScore() + "): Продукт=" + strategy.productName()
                + " -> Ставка: " + rate.multiply(ONE_HUNDRED) + "%");
        return rate;
    }
}
