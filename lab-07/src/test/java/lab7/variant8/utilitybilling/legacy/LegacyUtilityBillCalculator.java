package lab7.variant8.utilitybilling.legacy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ВИХІДНИЙ «God Object» із завдання 1 (клас UtilityBillCalculator), логіка не змінена.
 * Збережений у тестах лише для порівняння поведінки до/після рефакторингу.
 * Єдина зміна: файл реєстру винесено в константу REGISTRY_FILE.
 */
public class LegacyUtilityBillCalculator {

    public static final String REGISTRY_FILE = "utility_payments_db.csv";

    public void processMonthlyBill(LegacyAccountData account) {
        // 1. Валідація показників приладів обліку
        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank()) {
            throw new IllegalArgumentException("Номер особового рахунку обов'язковий.");
        }

        if (account.getCurrentElectricityMeterReading() < account.getPreviousElectricityMeterReading()) {
            throw new IllegalStateException("Поточні показники лічильника не можуть бути меншими за попередні.");
        }

        int consumptionKwh = account.getCurrentElectricityMeterReading() - account.getPreviousElectricityMeterReading();

        if (consumptionKwh > 5000) {
            throw new IllegalArgumentException("Аномально високе споживання електроенергії. Потрібна перевірка інспектора.");
        }

        // 2. Розрахунок за диференційованим тарифом та обчислення субсидії
        BigDecimal cost;
        if (consumptionKwh <= 100) {
            cost = BigDecimal.valueOf(consumptionKwh).multiply(new BigDecimal("2.64"));
        } else {
            BigDecimal first100 = new BigDecimal("100").multiply(new BigDecimal("2.64"));
            BigDecimal remainder = BigDecimal.valueOf(consumptionKwh - 100).multiply(new BigDecimal("4.32"));
            cost = first100.add(remainder);
        }

        BigDecimal subsidyDeduction = BigDecimal.ZERO;
        if (account.isHasSubsidy() && account.getSubsidyDiscountPercent() != null && account.getSubsidyDiscountPercent().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discountFraction = account.getSubsidyDiscountPercent().divide(new BigDecimal("100.0"));
            subsidyDeduction = cost.multiply(discountFraction);
        }

        BigDecimal totalToPay = cost.subtract(subsidyDeduction);

        // 3. Генерація офіційної платіжної квитанції
        StringBuilder billInvoice = new StringBuilder();
        billInvoice.append("==================================================\n");
        billInvoice.append("КВИТАНЦІЯ ПРО ОПЛАТУ КОМУНАЛЬНИХ ПОСЛУГ\n");
        billInvoice.append(String.format("Особовий рахунок: %s | ПІБ: %s%n", account.getAccountNumber(), account.getOwnerName()));
        billInvoice.append(String.format("Спожито електроенергії: %d кВт*год%n", consumptionKwh));
        billInvoice.append(String.format("Нараховано за тарифом: %.2f грн%n", cost));
        if (account.isHasSubsidy()) {
            billInvoice.append(String.format("Субсидія/Пільга (%s%%): -%.2f грн%n", account.getSubsidyDiscountPercent(), subsidyDeduction));
        }
        billInvoice.append(String.format("ДО СПЛАТИ: %.2f грн%n", totalToPay));
        billInvoice.append("==================================================\n");

        // 4. Збереження у базі платіжного реєстру
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String logLine = String.format("%s;%d;%.2f;%s%n", account.getAccountNumber(), consumptionKwh, totalToPay, dateStr);
        try (FileOutputStream fos = new FileOutputStream(REGISTRY_FILE, true)) {
            fos.write(logLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Помилка збереження квитанції: " + e.getMessage());
        }

        // 5. Відправка SMS з посиланням на онлайн-оплату
        try {
            String paymentUrl = String.format("http://pay.utility.local/bill?acc=%s&sum=%.2f", account.getAccountNumber(), totalToPay);
            String smsContent = String.format("Рахунок за комунальні послуги: %.2f грн. Оплата онлайн: %s", totalToPay, paymentUrl);

            System.out.println("Надсилання SMS на " + account.getOwnerPhone() + " через http://sms-gateway.local/send");
            System.out.println("Текст: " + smsContent);
        } catch (Exception ex) {
            System.out.println("Помилка відправки SMS-посилання: " + ex.getMessage());
        }
    }
}
