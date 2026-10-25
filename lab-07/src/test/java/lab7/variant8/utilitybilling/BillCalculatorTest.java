package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BillCalculatorTest extends FixedLocaleTest {

    private final BillCalculator calculator = new BillCalculator(new TariffCalculator(), new SubsidyCalculator());

    @Test
    @DisplayName("Розрахунок із субсидією 20%: 250 кВт·год → 912.00 − 182.40 = 729.60")
    void withSubsidy() {
        BillingResult result = calculator.calculate(TestAccounts.account("A", 1000, 1250, true, "20"));

        assertEquals(250, result.consumptionKwh());
        assertAmount("912.00", result.tariffCost());
        assertAmount("182.40", result.subsidyDeduction());
        assertAmount("729.60", result.totalToPay());
    }

    @Test
    @DisplayName("Розрахунок без субсидії")
    void withoutSubsidy() {
        BillingResult result = calculator.calculate(TestAccounts.account("A", 0, 50, false, null));

        assertAmount("132.00", result.tariffCost());
        assertAmount("0", result.subsidyDeduction());
        assertAmount("132.00", result.totalToPay());
    }

    @Test
    @DisplayName("Залежності обов'язкові")
    void dependenciesAreRequired() {
        assertThrows(NullPointerException.class, () -> new BillCalculator(null, new SubsidyCalculator()));
        assertThrows(NullPointerException.class, () -> new BillCalculator(new TariffCalculator(), null));
    }
}
