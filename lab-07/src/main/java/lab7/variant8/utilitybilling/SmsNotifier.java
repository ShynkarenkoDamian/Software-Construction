package lab7.variant8.utilitybilling;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Єдина причина для зміни: канал і текст SMS із посиланням на онлайн-оплату.
 * Відправка симулюється виведенням у потік, як в оригінальному коді.
 */
public class SmsNotifier {

    private final PrintStream out;

    public SmsNotifier(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out");
    }

    public void sendPaymentLink(UtilityAccountData account, BigDecimal totalToPay) {
        String paymentUrl = String.format("http://pay.utility.local/bill?acc=%s&sum=%.2f",
                account.getAccountNumber(), totalToPay);
        String smsContent = String.format("Рахунок за комунальні послуги: %.2f грн. Оплата онлайн: %s",
                totalToPay, paymentUrl);

        out.println("Надсилання SMS на " + account.getOwnerPhone() + " через http://sms-gateway.local/send");
        out.println("Текст: " + smsContent);
    }
}
