package lab7.variant8.utilitybilling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Єдина причина для зміни: спосіб і формат збереження рахунку в реєстр платіжної системи (CSV-файл).
 */
public class PaymentRegistryWriter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Path registryFile;
    private final Clock clock;

    public PaymentRegistryWriter(Path registryFile, Clock clock) {
        this.registryFile = Objects.requireNonNull(registryFile, "registryFile");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    /** Дописує рядок «рахунок;кВт·год;сума;дата». Помилку запису лише повідомляє в stderr, як в оригіналі. */
    public void save(UtilityAccountData account, BillingResult result) {
        String dateStr = LocalDate.now(clock).format(DATE_FORMAT);
        String logLine = String.format("%s;%d;%.2f;%s%n",
                account.getAccountNumber(), result.consumptionKwh(), result.totalToPay(), dateStr);
        try (FileOutputStream fos = new FileOutputStream(registryFile.toFile(), true)) {
            fos.write(logLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Помилка збереження квитанції: " + e.getMessage());
        }
    }
}
