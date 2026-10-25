package lab7.variant8.loaninterest;

import lab7.variant8.loaninterest.strategy.AutoLoanStrategy;
import lab7.variant8.loaninterest.strategy.BusinessLoanStrategy;
import lab7.variant8.loaninterest.strategy.ConsumerLoanStrategy;
import lab7.variant8.loaninterest.strategy.MortgageStrategy;
import lab7.variant8.loaninterest.strategy.StudentLoanStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lab7.variant8.loaninterest.LoanTestSupport.applicant;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Межові значення кожної стратегії; ставка береться через калькулятор (scale 4). */
class StrategiesTest {

    private final LoanInterestCalculator calculator = new LoanInterestCalculator();

    private String rate(InterestRateStrategy strategy, int score, String income) {
        return calculator.calculateInterestRate(applicant(score, income), strategy).toPlainString();
    }

    @Test
    @DisplayName("Іпотека: > 750 знижка, < 650 надбавка, між ними базова")
    void mortgage() {
        MortgageStrategy s = new MortgageStrategy();
        assertEquals("0.0650", rate(s, 751, "1000.00"));
        assertEquals("0.0750", rate(s, 750, "1000.00"));
        assertEquals("0.0750", rate(s, 650, "1000.00"));
        assertEquals("0.0900", rate(s, 649, "1000.00"));
    }

    @Test
    @DisplayName("Споживчий: знижка за дохід >= 3000.00 і надбавка за скоринг < 600 діють незалежно")
    void consumerLoan() {
        ConsumerLoanStrategy s = new ConsumerLoanStrategy();
        assertEquals("0.1600", rate(s, 600, "3000.00"));
        assertEquals("0.1800", rate(s, 600, "2999.99"));
        assertEquals("0.2000", rate(s, 599, "3000.00"));
        assertEquals("0.2200", rate(s, 599, "2999.99"));
    }

    @Test
    @DisplayName("Автокредит: знижка при скорингу >= 700")
    void autoLoan() {
        AutoLoanStrategy s = new AutoLoanStrategy();
        assertEquals("0.0950", rate(s, 700, "1000.00"));
        assertEquals("0.1100", rate(s, 699, "1000.00"));
    }

    @Test
    @DisplayName("Студентський: фіксована ставка незалежно від заявника")
    void studentLoan() {
        StudentLoanStrategy s = new StudentLoanStrategy();
        assertEquals("0.0450", rate(s, 300, "0.00"));
        assertEquals("0.0450", rate(s, 850, "99999.00"));
    }

    @Test
    @DisplayName("Бізнес-кредит (демонстраційне розширення): знижка при скорингу >= 700")
    void businessLoan() {
        BusinessLoanStrategy s = new BusinessLoanStrategy();
        assertEquals("0.1200", rate(s, 700, "1000.00"));
        assertEquals("0.1300", rate(s, 699, "1000.00"));
    }

    @Test
    @DisplayName("Назви продуктів збігаються з колишніми константами enum")
    void productNames() {
        assertEquals("MORTGAGE", new MortgageStrategy().productName());
        assertEquals("CONSUMER_LOAN", new ConsumerLoanStrategy().productName());
        assertEquals("AUTO_LOAN", new AutoLoanStrategy().productName());
        assertEquals("STUDENT_LOAN", new StudentLoanStrategy().productName());
    }
}
