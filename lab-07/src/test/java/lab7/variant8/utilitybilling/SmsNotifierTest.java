package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmsNotifierTest extends FixedLocaleTest {

    @Test
    @DisplayName("SMS містить суму та посилання на онлайн-оплату")
    void sendsPaymentLink() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        SmsNotifier notifier = new SmsNotifier(new PrintStream(captured, true, StandardCharsets.UTF_8));
        String nl = System.lineSeparator();

        notifier.sendPaymentLink(TestAccounts.account("ACC-001", 0, 250, false, null), new BigDecimal("729.60"));

        assertEquals("Надсилання SMS на +380501234567 через http://sms-gateway.local/send" + nl
                        + "Текст: Рахунок за комунальні послуги: 729.60 грн. Оплата онлайн: "
                        + "http://pay.utility.local/bill?acc=ACC-001&sum=729.60" + nl,
                captured.toString(StandardCharsets.UTF_8));
    }
}
