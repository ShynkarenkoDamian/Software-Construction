package lab7.variant8.loaninterest.legacy;

import lab7.variant8.loaninterest.LoanApplicant;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ВИХІДНИЙ код завдання 2 (switch за enum), логіка не змінена.
 * Збережений у тестах лише для порівняння поведінки до/після рефакторингу.
 */
public class LegacyLoanInterestCalculator {

    public enum LoanType {
        MORTGAGE,
        CONSUMER_LOAN,
        AUTO_LOAN,
        STUDENT_LOAN
    }

    public BigDecimal calculateInterestRate(LoanApplicant applicant, LoanType type) {
        BigDecimal baseRate = BigDecimal.ZERO;

        switch (type) {
            case MORTGAGE:
                baseRate = new BigDecimal("0.075");
                if (applicant.getCreditScore() > 750) {
                    baseRate = baseRate.subtract(new BigDecimal("0.010"));
                } else if (applicant.getCreditScore() < 650) {
                    baseRate = baseRate.add(new BigDecimal("0.015"));
                }
                break;

            case CONSUMER_LOAN:
                baseRate = new BigDecimal("0.180");
                if (applicant.getMonthlyIncome().compareTo(new BigDecimal("3000.00")) >= 0) {
                    baseRate = baseRate.subtract(new BigDecimal("0.020"));
                }
                if (applicant.getCreditScore() < 600) {
                    baseRate = baseRate.add(new BigDecimal("0.040"));
                }
                break;

            case AUTO_LOAN:
                baseRate = new BigDecimal("0.110");
                if (applicant.getCreditScore() >= 700) {
                    baseRate = baseRate.subtract(new BigDecimal("0.015"));
                }
                break;

            case STUDENT_LOAN:
                baseRate = new BigDecimal("0.045");
                break;

            default:
                throw new IllegalArgumentException("Невідомий тип кредиту: " + type);
        }

        baseRate = baseRate.setScale(4, RoundingMode.HALF_UP);
        System.out.println("[LOAN] Заявка від " + applicant.getApplicantId() + " (Скоринг: " + applicant.getCreditScore() + "): Продукт=" + type + " -> Ставка: " + baseRate.multiply(new BigDecimal("100")) + "%");
        return baseRate;
    }
}
