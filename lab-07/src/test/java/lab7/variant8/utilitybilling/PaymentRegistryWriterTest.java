package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRegistryWriterTest extends FixedLocaleTest {

    private static final String NL = System.lineSeparator();
    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-01-15T10:00:00Z"), ZoneOffset.UTC);

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Дописує рядки «рахунок;кВт·год;сума;дата» у кінець файлу")
    void appendsLines() throws IOException {
        Path file = tempDir.resolve("registry.csv");
        PaymentRegistryWriter writer = new PaymentRegistryWriter(file, FIXED_CLOCK);

        writer.save(TestAccounts.account("ACC-001", 0, 250, false, null),
                new BillingResult(250, new BigDecimal("912.00"), BigDecimal.ZERO, new BigDecimal("729.60")));
        writer.save(TestAccounts.account("ACC-002", 0, 50, false, null),
                new BillingResult(50, new BigDecimal("132.00"), BigDecimal.ZERO, new BigDecimal("132.00")));

        assertEquals("ACC-001;250;729.60;2026-01-15" + NL + "ACC-002;50;132.00;2026-01-15" + NL,
                Files.readString(file, StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("Помилка запису не кидає виняток, а повідомляється в stderr (як в оригіналі)")
    void ioErrorIsReportedToStderr() {
        PrintStream originalErr = System.err;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setErr(new PrintStream(captured, true, StandardCharsets.UTF_8));
        try {
            // Шлях вказує на каталог, а не на файл — відкриття на запис завершиться IOException.
            new PaymentRegistryWriter(tempDir, FIXED_CLOCK).save(
                    TestAccounts.account("ACC-001", 0, 10, false, null),
                    new BillingResult(10, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE));
        } finally {
            System.setErr(originalErr);
        }

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("Помилка збереження квитанції: "));
    }
}
