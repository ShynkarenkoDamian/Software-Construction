package lab7.variant8.utilitybilling;

import java.math.BigDecimal;

/**
 * Незмінний результат розрахунку (Value Object), який передається
 * між калькулятором, форматувальником квитанції, реєстром та SMS-нотифікатором.
 *
 * @param consumptionKwh   спожито, кВт·год
 * @param tariffCost       нараховано за диференційованим тарифом
 * @param subsidyDeduction сума субсидії/пільги, що віднімається
 * @param totalToPay       до сплати (tariffCost − subsidyDeduction)
 */
public record BillingResult(int consumptionKwh,
                            BigDecimal tariffCost,
                            BigDecimal subsidyDeduction,
                            BigDecimal totalToPay) {
}
