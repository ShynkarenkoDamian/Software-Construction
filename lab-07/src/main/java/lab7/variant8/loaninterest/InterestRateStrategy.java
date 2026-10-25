package lab7.variant8.loaninterest;

import java.math.BigDecimal;

/**
 * ТОЧКА РОЗШИРЕННЯ (Extension Point) системи.
 * Кожен кредитний продукт — окрема реалізація цього інтерфейсу.
 * Новий продукт = новий клас; {@link LoanInterestCalculator} не змінюється.
 */
public interface InterestRateStrategy {

    /** Назва продукту для журналу (наприклад, "MORTGAGE"). */
    String productName();

    /** Річна ставка продукту для заявника у вигляді частки (0.075 = 7.5%), ще без остаточного округлення. */
    BigDecimal calculateRate(LoanApplicant applicant);
}
