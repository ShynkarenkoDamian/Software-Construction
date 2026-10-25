package lab7.variant8.loaninterest;

import java.math.BigDecimal;

/**
 * Позичальник (сутність). Без змін відносно вихідного коду, окрім final-полів.
 */
public class LoanApplicant {
    private final String applicantId;
    private final int creditScore; // Скоринговий бал 300-850
    private final BigDecimal monthlyIncome;
    private final BigDecimal requestedAmount;

    public LoanApplicant(String applicantId, int creditScore, BigDecimal monthlyIncome, BigDecimal requestedAmount) {
        this.applicantId = applicantId;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.requestedAmount = requestedAmount;
    }

    public String getApplicantId() { return applicantId; }
    public int getCreditScore() { return creditScore; }
    public BigDecimal getMonthlyIncome() { return monthlyIncome; }
    public BigDecimal getRequestedAmount() { return requestedAmount; }
}
