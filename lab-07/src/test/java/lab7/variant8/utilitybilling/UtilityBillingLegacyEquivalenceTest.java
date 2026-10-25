package lab7.variant8.utilitybilling;

import lab7.variant8.FixedLocaleTest;
import lab7.variant8.utilitybilling.legacy.LegacyUtilityBillCalculator;
import org.junit.jupiter.api.AfterEach;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Characterization-тест: рефакторинг не повинен змінити спостережувану поведінку.
 * Однакові вхідні дані подаються у вихідний «God Object» та в нову структуру;
 * порівнюється вміст реєстру (CSV) та вивід SMS. Для невалідних даних — тип і текст винятку.
 */
class UtilityBillingLegacyEquivalenceTest extends FixedLocaleTest {

    private record Case(String name, String number, int previous, int current, boolean subsidy, String percent) {
    }

    private static final List<Case> VALID_CASES = List.of(
            new Case("250 кВт·год, субсидія 20%", "ACC-001", 1000, 1250, true, "20"),
            new Case("50 кВт·год без субсидії", "ACC-002", 0, 50, false, null),
            new Case("рівно 100 кВт·год", "ACC-003", 10, 110, false, null),
            new Case("101 кВт·год", "ACC-004", 0, 101, false, null),
            new Case("нульове споживання", "ACC-005", 70, 70, false, null),
            new Case("рівно 5000 кВт·год", "ACC-006", 0, 5000, false, null),
            new Case("прапорець пільги є, відсотка немає", "ACC-007", 0, 300, true, null),
            new Case("прапорець пільги є, відсоток 0", "ACC-008", 0, 300, true, "0"),
            new Case("пільга 15.5%", "ACC-009", 0, 333, true, "15.5"),
            new Case("пільга 100%", "ACC-010", 0, 120, true, "100"),
            new Case("відсоток є, але права на пільгу немає", "ACC-011", 0, 120, false, "50"));

    private static final List<Case> INVALID_CASES = List.of(
            new Case("порожній номер", "  ", 0, 10, false, null),
            new Case("відсутній номер", null, 0, 10, false, null),
            new Case("показники зменшились", "ACC-020", 500, 100, false, null),
            new Case("аномальне споживання 5001", "ACC-021", 0, 5001, false, null));

    @TempDir
    Path tempDir;

    private final Path legacyFile = Path.of(LegacyUtilityBillCalculator.REGISTRY_FILE);
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws IOException {
        originalOut = System.out;
        Files.deleteIfExists(legacyFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        Files.deleteIfExists(legacyFile);
    }

    @Test
    @DisplayName("Валідні дані: реєстр і SMS ідентичні вихідному коду")
    void validCasesProduceSameObservableOutput() throws IOException {
        for (Case c : VALID_CASES) {
            Files.deleteIfExists(legacyFile);

            // --- вихідний код ---
            ByteArrayOutputStream legacyOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(legacyOut, true, StandardCharsets.UTF_8));
            new LegacyUtilityBillCalculator().processMonthlyBill(
                    TestAccounts.legacyAccount(c.number(), c.previous(), c.current(), c.subsidy(), c.percent()));
            System.setOut(originalOut);

            // --- рефакторинг ---
            Path newFile = tempDir.resolve("new_" + c.number() + ".csv");
            ByteArrayOutputStream newOut = new ByteArrayOutputStream();
            new UtilityBillingService(
                    new AccountValidator(),
                    new BillCalculator(new TariffCalculator(), new SubsidyCalculator()),
                    new ReceiptFormatter(),
                    new PaymentRegistryWriter(newFile, Clock.systemDefaultZone()),
                    new SmsNotifier(new PrintStream(newOut, true, StandardCharsets.UTF_8)))
                    .processMonthlyBill(
                            TestAccounts.account(c.number(), c.previous(), c.current(), c.subsidy(), c.percent()));

            assertEquals(Files.readString(legacyFile), Files.readString(newFile), "Реєстр: " + c.name());
            assertEquals(legacyOut.toString(StandardCharsets.UTF_8), newOut.toString(StandardCharsets.UTF_8),
                    "SMS: " + c.name());
        }
    }

    @Test
    @DisplayName("Невалідні дані: той самий тип і текст винятку, без побічних ефектів")
    void invalidCasesThrowSameExceptions() {
        for (Case c : INVALID_CASES) {
            RuntimeException legacy = assertThrows(RuntimeException.class,
                    () -> new LegacyUtilityBillCalculator().processMonthlyBill(
                            TestAccounts.legacyAccount(c.number(), c.previous(), c.current(), c.subsidy(), c.percent())),
                    "legacy: " + c.name());

            Path newFile = tempDir.resolve("invalid.csv");
            RuntimeException refactored = assertThrows(RuntimeException.class,
                    () -> new UtilityBillingService(
                            new AccountValidator(),
                            new BillCalculator(new TariffCalculator(), new SubsidyCalculator()),
                            new ReceiptFormatter(),
                            new PaymentRegistryWriter(newFile, Clock.systemDefaultZone()),
                            new SmsNotifier(new PrintStream(new ByteArrayOutputStream())))
                            .processMonthlyBill(
                                    TestAccounts.account(c.number(), c.previous(), c.current(), c.subsidy(), c.percent())),
                    "refactored: " + c.name());

            assertEquals(legacy.getClass(), refactored.getClass(), "Тип винятку: " + c.name());
            assertEquals(legacy.getMessage(), refactored.getMessage(), "Текст винятку: " + c.name());
        }
    }
}
