package lab7.variant8.utilitybilling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountValidatorTest {

    private final AccountValidator validator = new AccountValidator();

    @Test
    @DisplayName("Коректний рахунок проходить валідацію")
    void validAccountPasses() {
        assertDoesNotThrow(() -> validator.validate(TestAccounts.account("ACC-1", 100, 250, false, null)));
    }

    @Test
    @DisplayName("Нульове споживання допустиме (показники рівні)")
    void zeroConsumptionIsAllowed() {
        assertDoesNotThrow(() -> validator.validate(TestAccounts.account("ACC-1", 100, 100, false, null)));
    }

    @Test
    @DisplayName("Рівно 5000 кВт·год — ще допустимо")
    void exactlyThresholdIsAllowed() {
        assertDoesNotThrow(() -> validator.validate(TestAccounts.account("ACC-1", 0, 5000, false, null)));
    }

    @Test
    @DisplayName("5001 кВт·год — аномальне споживання")
    void aboveThresholdIsRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(TestAccounts.account("ACC-1", 0, 5001, false, null)));
        assertEquals("Аномально високе споживання електроенергії. Потрібна перевірка інспектора.", ex.getMessage());
    }

    @Test
    @DisplayName("Порожній або відсутній номер рахунку")
    void blankAccountNumberIsRejected() {
        IllegalArgumentException blank = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(TestAccounts.account("   ", 0, 10, false, null)));
        assertEquals("Номер особового рахунку обов'язковий.", blank.getMessage());

        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(TestAccounts.account(null, 0, 10, false, null)));
    }

    @Test
    @DisplayName("Поточні показники менші за попередні")
    void decreasingReadingIsRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> validator.validate(TestAccounts.account("ACC-1", 200, 100, false, null)));
        assertEquals("Поточні показники лічильника не можуть бути меншими за попередні.", ex.getMessage());
    }

    @Test
    @DisplayName("null замість рахунку — fail-fast")
    void nullAccountFailsFast() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }
}
