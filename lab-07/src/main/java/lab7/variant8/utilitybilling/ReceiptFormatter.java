package lab7.variant8.utilitybilling;

/**
 * Єдина причина для зміни: вигляд текстової квитанції.
 */
public class ReceiptFormatter {

    private static final String SEPARATOR = "==================================================\n";

    public String format(UtilityAccountData account, BillingResult result) {
        StringBuilder bill = new StringBuilder();
        bill.append(SEPARATOR);
        bill.append("КВИТАНЦІЯ ПРО ОПЛАТУ КОМУНАЛЬНИХ ПОСЛУГ\n");
        bill.append(String.format("Особовий рахунок: %s | ПІБ: %s%n", account.getAccountNumber(), account.getOwnerName()));
        bill.append(String.format("Спожито електроенергії: %d кВт*год%n", result.consumptionKwh()));
        bill.append(String.format("Нараховано за тарифом: %.2f грн%n", result.tariffCost()));
        if (account.isHasSubsidy()) {
            bill.append(String.format("Субсидія/Пільга (%s%%): -%.2f грн%n",
                    account.getSubsidyDiscountPercent(), result.subsidyDeduction()));
        }
        bill.append(String.format("ДО СПЛАТИ: %.2f грн%n", result.totalToPay()));
        bill.append(SEPARATOR);
        return bill.toString();
    }
}
