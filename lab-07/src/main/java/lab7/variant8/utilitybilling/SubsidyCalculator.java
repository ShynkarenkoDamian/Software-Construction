package lab7.variant8.utilitybilling;

import java.math.BigDecimal;

/**
 * Єдина причина для зміни: правила нарахування субсидії/пільги.
 */
public class SubsidyCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.0");

    /**
     * @return сума, що віднімається від вартості; {@link BigDecimal#ZERO}, якщо пільги немає
     */
    public BigDecimal calculateDeduction(UtilityAccountData account, BigDecimal cost) {
        if (!account.isHasSubsidy()) {
            return BigDecimal.ZERO;
        }

        BigDecimal percent = account.getSubsidyDiscountPercent();
        if (percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return cost.multiply(percent.divide(ONE_HUNDRED));
    }
}
