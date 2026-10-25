package lab7.variant8.utilitybilling;

import java.math.BigDecimal;

/**
 * Єдина причина для зміни: диференційований тариф на електроенергію.
 * Перші 100 кВт·год — за нижчою ціною, решта — за вищою.
 */
public class TariffCalculator {

    public static final int FIRST_TIER_LIMIT_KWH = 100;
    public static final BigDecimal FIRST_TIER_RATE = new BigDecimal("2.64");
    public static final BigDecimal SECOND_TIER_RATE = new BigDecimal("4.32");

    public BigDecimal calculateCost(int consumptionKwh) {
        if (consumptionKwh < 0) {
            throw new IllegalArgumentException("Споживання не може бути від'ємним: " + consumptionKwh);
        }

        int firstTierKwh = Math.min(consumptionKwh, FIRST_TIER_LIMIT_KWH);
        int secondTierKwh = consumptionKwh - firstTierKwh;

        return BigDecimal.valueOf(firstTierKwh).multiply(FIRST_TIER_RATE)
                .add(BigDecimal.valueOf(secondTierKwh).multiply(SECOND_TIER_RATE));
    }
}
