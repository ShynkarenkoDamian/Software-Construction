package lab7.variant8.utilitybilling;

import java.util.Objects;

/**
 * Клас-оркестратор: лише визначає ПОРЯДОК кроків і не містить жодних правил.
 * validate → calculate → format → save → notify.
 */
public class UtilityBillingService {

    private final AccountValidator validator;
    private final BillCalculator calculator;
    private final ReceiptFormatter receiptFormatter;
    private final PaymentRegistryWriter registryWriter;
    private final SmsNotifier smsNotifier;

    public UtilityBillingService(AccountValidator validator,
                                 BillCalculator calculator,
                                 ReceiptFormatter receiptFormatter,
                                 PaymentRegistryWriter registryWriter,
                                 SmsNotifier smsNotifier) {
        this.validator = Objects.requireNonNull(validator, "validator");
        this.calculator = Objects.requireNonNull(calculator, "calculator");
        this.receiptFormatter = Objects.requireNonNull(receiptFormatter, "receiptFormatter");
        this.registryWriter = Objects.requireNonNull(registryWriter, "registryWriter");
        this.smsNotifier = Objects.requireNonNull(smsNotifier, "smsNotifier");
    }

    /**
     * @return текст квитанції (в оригіналі вона формувалась, але ніде не використовувалась)
     */
    public String processMonthlyBill(UtilityAccountData account) {
        validator.validate(account);

        BillingResult result = calculator.calculate(account);
        String receipt = receiptFormatter.format(account, result);

        registryWriter.save(account, result);
        smsNotifier.sendPaymentLink(account, result.totalToPay());

        return receipt;
    }
}
