package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class SubsidyCalculatorTest extends FixedLocaleTest {

    private final SubsidyCalculator calculator = new SubsidyCalculator();

    @Test
    @DisplayName("Без права на пільгу відсоток ігнорується")
    void noSubsidyFlag() {
        UtilityAccountData account = TestAccounts.account("A", 0, 10, false, "50");
        assertAmount("0", calculator.calculateDeduction(account, new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Пільга є, але відсоток відсутній, нульовий або від'ємний — знижки немає")
    void subsidyWithoutValidPercent() {
        BigDecimal cost = new BigDecimal("100.00");
        assertAmount("0", calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, null), cost));
        assertAmount("0", calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, "0"), cost));
        assertAmount("0", calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, "-5"), cost));
    }

    @Test
    @DisplayName("Відсоток застосовується до вартості")
    void percentIsApplied() {
        assertAmount("182.40",
                calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, "20"), new BigDecimal("912.00")));
        assertAmount("100",
                calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, "100"), new BigDecimal("100.00")));
        assertAmount("31",
                calculator.calculateDeduction(TestAccounts.account("A", 0, 10, true, "15.5"), new BigDecimal("200.00")));
    }
}
