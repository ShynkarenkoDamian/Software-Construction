package ua.miskavto.lab15.task2_template_method;

import ua.miskavto.lab15.common.Log;

/** ConcreteClass: гібрид. */
public class HybridCarInspection extends CarInspection {

    private static final int MIN_ENERGY_PERCENT = 15;

    @Override
    protected boolean checkEnergy(Car car) {
        boolean ok = car.getEnergyPercent() >= MIN_ENERGY_PERCENT;
        Log.log(source(), "Етап 3. Рівень палива/заряду " + car.getEnergyPercent() + "% (мінімум "
                + MIN_ENERGY_PERCENT + "%) — " + (ok ? "достатньо" : "недостатньо"));
        return ok;
    }

    @Override
    protected void technicalCheck(Car car) {
        Log.log(source(), "Етап 4. Технічна перевірка: двигун внутрішнього згоряння, тяговий акумулятор, гальма");
    }
}
