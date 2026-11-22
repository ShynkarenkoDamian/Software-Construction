package ua.miskavto.lab15.task2_template_method;

import ua.miskavto.lab15.common.Log;

/**
 * ConcreteClass: авто на газу.
 * Додано ОДНИМ класом: CarInspection та інші типи огляду не змінювалися.
 */
public class GasCarInspection extends CarInspection {

    private static final int MIN_GAS_PERCENT = 15;

    @Override
    protected boolean checkEnergy(Car car) {
        boolean ok = car.getEnergyPercent() >= MIN_GAS_PERCENT;
        Log.log(source(), "Етап 3. Рівень газу " + car.getEnergyPercent() + "% (мінімум " + MIN_GAS_PERCENT
                + "%) — " + (ok ? "достатньо" : "недостатньо"));
        return ok;
    }

    @Override
    protected void technicalCheck(Car car) {
        Log.log(source(), "Етап 4. Технічна перевірка: газовий балон, редуктор, герметичність магістралей");
    }
}
