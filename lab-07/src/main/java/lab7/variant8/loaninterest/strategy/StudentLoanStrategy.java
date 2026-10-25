package lab7.variant8.loaninterest.strategy;

import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;

/** Студентський кредит: фіксована соціальна ставка 4.5%. */
public class StudentLoanStrategy implements InterestRateStrategy {

    private static final BigDecimal FIXED_RATE = new BigDecimal("0.045");

    @Override
    public String productName() {
        return "STUDENT_LOAN";
    }

    @Override
    public BigDecimal calculateRate(LoanApplicant applicant) {
        return FIXED_RATE;
    }
}
