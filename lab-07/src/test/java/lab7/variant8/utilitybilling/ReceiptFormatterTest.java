package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptFormatterTest extends FixedLocaleTest {

    private static final String SEPARATOR = "=".repeat(50) + "\n";
    private static final String NL = System.lineSeparator();

    private final ReceiptFormatter formatter = new ReceiptFormatter();

    @Test
    @DisplayName("Квитанція з рядком субсидії")
    void receiptWithSubsidy() {
        UtilityAccountData account = TestAccounts.account("ACC-001", 1000, 1250, true, "20");
        BillingResult result = new BillingResult(250, new BigDecimal("912.00"),
                new BigDecimal("182.40"), new BigDecimal("729.60"));

        String expected = SEPARATOR
                + "КВИТАНЦІЯ ПРО ОПЛАТУ КОМУНАЛЬНИХ ПОСЛУГ\n"
                + "Особовий рахунок: ACC-001 | ПІБ: Іваненко Іван Іванович" + NL
                + "Спожито електроенергії: 250 кВт*год" + NL
                + "Нараховано за тарифом: 912.00 грн" + NL
                + "Субсидія/Пільга (20%): -182.40 грн" + NL
                + "ДО СПЛАТИ: 729.60 грн" + NL
                + SEPARATOR;

        assertEquals(expected, formatter.format(account, result));
    }

    @Test
    @DisplayName("Квитанція без рядка субсидії")
    void receiptWithoutSubsidy() {
        UtilityAccountData account = TestAccounts.account("ACC-002", 0, 50, false, null);
        BillingResult result = new BillingResult(50, new BigDecimal("132.00"),
                BigDecimal.ZERO, new BigDecimal("132.00"));

        String expected = SEPARATOR
                + "КВИТАНЦІЯ ПРО ОПЛАТУ КОМУНАЛЬНИХ ПОСЛУГ\n"
                + "Особовий рахунок: ACC-002 | ПІБ: Іваненко Іван Іванович" + NL
                + "Спожито електроенергії: 50 кВт*год" + NL
                + "Нараховано за тарифом: 132.00 грн" + NL
                + "ДО СПЛАТИ: 132.00 грн" + NL
                + SEPARATOR;

        assertEquals(expected, formatter.format(account, result));
    }
}
