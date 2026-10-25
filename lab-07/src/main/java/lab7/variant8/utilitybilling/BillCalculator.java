package lab7.variant8.utilitybilling;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Бізнес-логіка розрахунку: поєднує тариф і субсидію в {@link BillingResult}.
 * Не знає ні про валідацію, ні про форматування, ні про збереження.
 */
public class BillCalculator {

    private final TariffCalculator tariffCalculator;
    private final SubsidyCalculator subsidyCalculator;

    public BillCalculator(TariffCalculator tariffCalculator, SubsidyCalculator subsidyCalculator) {
        this.tariffCalculator = Objects.requireNonNull(tariffCalculator, "tariffCalculator");
        this.subsidyCalculator = Objects.requireNonNull(subsidyCalculator, "subsidyCalculator");
    }

    public BillingResult calculate(UtilityAccountData account) {
        int consumptionKwh = account.getConsumptionKwh();
        BigDecimal cost = tariffCalculator.calculateCost(consumptionKwh);
        BigDecimal subsidyDeduction = subsidyCalculator.calculateDeduction(account, cost);
        BigDecimal totalToPay = cost.subtract(subsidyDeduction);
        return new BillingResult(consumptionKwh, cost, subsidyDeduction, totalToPay);
    }
}
