package lab7.variant8.utilitybilling;

import lab7.variant8.utilitybilling.legacy.LegacyAccountData;

import java.math.BigDecimal;

/** Фабрика тестових даних (спільна для нової та legacy-моделі). */
final class TestAccounts {

    private TestAccounts() {
    }

    static UtilityAccountData account(String number, int previous, int current, boolean subsidy, String percent) {
        UtilityAccountData account = new UtilityAccountData();
        account.setAccountNumber(number);
        account.setOwnerName("Іваненко Іван Іванович");
        account.setOwnerPhone("+380501234567");
        account.setPreviousElectricityMeterReading(previous);
        account.setCurrentElectricityMeterReading(current);
        account.setHasSubsidy(subsidy);
        account.setSubsidyDiscountPercent(percent == null ? null : new BigDecimal(percent));
        return account;
    }

    static LegacyAccountData legacyAccount(String number, int previous, int current, boolean subsidy, String percent) {
        LegacyAccountData account = new LegacyAccountData();
        account.setAccountNumber(number);
        account.setOwnerName("Іваненко Іван Іванович");
        account.setOwnerPhone("+380501234567");
        account.setPreviousElectricityMeterReading(previous);
        account.setCurrentElectricityMeterReading(current);
        account.setHasSubsidy(subsidy);
        account.setSubsidyDiscountPercent(percent == null ? null : new BigDecimal(percent));
        return account;
    }
}
