package lab7.variant8.loaninterest.strategy;

import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;

/** Іпотека: базово 7.5%, знижка 1.0% якщо скоринг > 750, надбавка 1.5% якщо скоринг < 650. */
public class MortgageStrategy implements InterestRateStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("0.075");
    private static final BigDecimal DISCOUNT = new BigDecimal("0.010");
    private static final BigDecimal SURCHARGE = new BigDecimal("0.015");
    private static final int DISCOUNT_SCORE_ABOVE = 750;
    private static final int SURCHARGE_SCORE_BELOW = 650;

    @Override
    public String productName() {
        return "MORTGAGE";
    }

    @Override
    public BigDecimal calculateRate(LoanApplicant applicant) {
        if (applicant.getCreditScore() > DISCOUNT_SCORE_ABOVE) {
            return BASE_RATE.subtract(DISCOUNT);
        }
        if (applicant.getCreditScore() < SURCHARGE_SCORE_BELOW) {
            return BASE_RATE.add(SURCHARGE);
        }
        return BASE_RATE;
    }
}
