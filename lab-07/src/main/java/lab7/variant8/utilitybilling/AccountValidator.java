package lab7.variant8.utilitybilling;

import java.util.Objects;

/**
 * Єдина причина для зміни: правила валідації особового рахунку та показників лічильника.
 */
public class AccountValidator {

    /** Поріг аномального споживання, кВт·год. */
    public static final int MAX_CONSUMPTION_KWH = 5000;

    public void validate(UtilityAccountData account) {
        Objects.requireNonNull(account, "account");

        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank()) {
            throw new IllegalArgumentException("Номер особового рахунку обов'язковий.");
        }

        if (account.getCurrentElectricityMeterReading() < account.getPreviousElectricityMeterReading()) {
            throw new IllegalStateException("Поточні показники лічильника не можуть бути меншими за попередні.");
        }

        if (account.getConsumptionKwh() > MAX_CONSUMPTION_KWH) {
            throw new IllegalArgumentException(
                    "Аномально високе споживання електроенергії. Потрібна перевірка інспектора.");
        }
    }
}
