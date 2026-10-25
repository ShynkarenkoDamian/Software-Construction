package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilityBillingServiceTest extends FixedLocaleTest {

    private static final String NL = System.lineSeparator();
    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-01-15T10:00:00Z"), ZoneOffset.UTC);

    @TempDir
    Path tempDir;

    private Path registry;
    private ByteArrayOutputStream smsOutput;
    private UtilityBillingService service;

    @BeforeEach
    void setUp() {
        registry = tempDir.resolve("registry.csv");
        smsOutput = new ByteArrayOutputStream();
        service = new UtilityBillingService(
                new AccountValidator(),
                new BillCalculator(new TariffCalculator(), new SubsidyCalculator()),
                new ReceiptFormatter(),
                new PaymentRegistryWriter(registry, FIXED_CLOCK),
                new SmsNotifier(new PrintStream(smsOutput, true, StandardCharsets.UTF_8)));
    }

    @Test
    @DisplayName("Повний сценарій: квитанція, запис у реєстр і SMS")
    void happyPath() throws IOException {
        String receipt = service.processMonthlyBill(TestAccounts.account("ACC-001", 1000, 1250, true, "20"));

        assertTrue(receipt.contains("ДО СПЛАТИ: 729.60 грн"));
        assertEquals("ACC-001;250;729.60;2026-01-15" + NL, Files.readString(registry));
        assertTrue(smsOutput.toString(StandardCharsets.UTF_8).contains("sum=729.60"));
    }

    @Test
    @DisplayName("Невалідні дані: виняток, і ні запису в реєстр, ні SMS")
    void invalidAccountHasNoSideEffects() {
        assertThrows(IllegalStateException.class,
                () -> service.processMonthlyBill(TestAccounts.account("ACC-001", 500, 100, false, null)));

        assertFalse(Files.exists(registry));
        assertEquals(0, smsOutput.size());
    }

    @Test
    @DisplayName("Залежності оркестратора обов'язкові")
    void dependenciesAreRequired() {
        assertThrows(NullPointerException.class, () -> new UtilityBillingService(
                null, null, null, null, null));
    }
}
