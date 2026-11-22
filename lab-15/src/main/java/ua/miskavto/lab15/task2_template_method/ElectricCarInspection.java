package ua.miskavto.lab15.task2_template_method;

import ua.miskavto.lab15.common.Log;

/** ConcreteClass: електромобіль. Якщо заряду мало — авто відправляється на зарядку. */
public class ElectricCarInspection extends CarInspection {

    private static final int MIN_CHARGE_PERCENT = 20;

    @Override
    protected boolean checkEnergy(Car car) {
        boolean ok = car.getEnergyPercent() >= MIN_CHARGE_PERCENT;
        Log.log(source(), "Етап 3. Рівень заряду " + car.getEnergyPercent() + "% (мінімум " + MIN_CHARGE_PERCENT
                + "%) — " + (ok ? "достатньо" : "недостатньо"));
        return ok;
    }

    @Override
    protected void technicalCheck(Car car) {
        Log.log(source(), "Етап 4. Технічна перевірка: тяговий акумулятор, зарядний порт, гальма");
    }

    @Override
    protected void sendForEnergy(Car car) {
        car.setStatus(CarStatus.SENT_FOR_ENERGY);
        Log.log(source(), "Етап 5. Заряд низький — авто відправлено на зарядку замість повернення в доступні");
    }
}
