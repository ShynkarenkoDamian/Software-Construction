package lab9.task2_prototype;

import lab9.common.ConsoleLogger;

/**
 * Виконує «дороге» налаштування тарифу з нуля. Викликається один раз —
 * для стандартного тарифу (еталона). Корпоративні тарифи отримуються копіюванням.
 */
public final class TariffConfigurator {

    private static final String SOURCE = "TariffConfigurator";

    private TariffConfigurator() {
    }

    public static TariffPlan configureStandardTariff() {
        ConsoleLogger.expensiveOperation(SOURCE,
                "Налаштування стандартного тарифу з нуля: ціни, страхування, зони завершення, "
                        + "обмеження швидкості, бонусна програма...",
                "Стандартний тариф налаштовано",
                20);

        TariffPlan tariff = new TariffPlan("Стандартний", 5.00, 7.00, new Insurance(InsuranceType.BASIC), 90);
        tariff.addEndZone(new EndZone("Центр"));
        tariff.addEndZone(new EndZone("Північний район"));
        tariff.addBonus(new Bonus("Кешбек за поїздку", 5));
        return tariff;
    }
}
