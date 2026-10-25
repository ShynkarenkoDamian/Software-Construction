package lab7.variant8.loaninterest.strategy;

import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;

/**
 * Споживчий кредит: базово 18.0%; знижка 2% при доході від 3000.00;
 * надбавка 4% при скорингу < 600 (умови незалежні й можуть діяти разом).
 */
public class ConsumerLoanStrategy implements InterestRateStrategy {

    private static final BigDecimal BASE_RATE = new BigDecimal("0.180");
    private static final BigDecimal HIGH_INCOME_DISCOUNT = new BigDecimal("0.020");
    private static final BigDecimal LOW_SCORE_SURCHARGE = new BigDecimal("0.040");
    private static final BigDecimal HIGH_INCOME_THRESHOLD = new BigDecimal("3000.00");
    private static final int LOW_SCORE_BELOW = 600;

    @Override
    public String productName() {
        return "CONSUMER_LOAN";
    }

    @Override
    public BigDecimal calculateRate(LoanApplicant applicant) {
        BigDecimal rate = BASE_RATE;
        if (applicant.getMonthlyIncome().compareTo(HIGH_INCOME_THRESHOLD) >= 0) {
            rate = rate.subtract(HIGH_INCOME_DISCOUNT);
        }
        if (applicant.getCreditScore() < LOW_SCORE_BELOW) {
            rate = rate.add(LOW_SCORE_SURCHARGE);
        }
        return rate;
    }
}
