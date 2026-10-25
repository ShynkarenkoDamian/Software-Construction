package lab7.variant8.loaninterest.strategy;

import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;

/** Автокредит: базово 11.0%, знижка 1.5% при скорингу >= 700. */
public class AutoLoanStrategy implements InterestRateStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("0.110");
    private static final BigDecimal DISCOUNT = new BigDecimal("0.015");
    private static final int DISCOUNT_SCORE_FROM = 700;

    @Override
    public String productName() {
        return "AUTO_LOAN";
    }

    @Override
    public BigDecimal calculateRate(LoanApplicant applicant) {
        if (applicant.getCreditScore() >= DISCOUNT_SCORE_FROM) {
            return BASE_RATE.subtract(DISCOUNT);
        }
        return BASE_RATE;
    }
}
