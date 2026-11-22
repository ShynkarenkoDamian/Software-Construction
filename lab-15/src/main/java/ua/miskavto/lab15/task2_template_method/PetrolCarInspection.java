package ua.miskavto.lab15.task2_template_method;

import ua.miskavto.lab15.common.Log;

/** ConcreteClass: бензинове авто. Перевизначає також необов'язковий етап «мийка». */
public class PetrolCarInspection extends CarInspection {

    private static final int MIN_FUEL_PERCENT = 15;

    @Override
    protected void wash(Car car) {
        Log.log(source(), "(необов'язковий етап) Мийка кузова виконана");
    }

    @Override
    protected boolean checkEnergy(Car car) {
        boolean ok = car.getEnergyPercent() >= MIN_FUEL_PERCENT;
        Log.log(source(), "Етап 3. Рівень палива " + car.getEnergyPercent() + "% (мінімум " + MIN_FUEL_PERCENT
                + "%) — " + (ok ? "достатньо" : "недостатньо"));
        return ok;
    }

    @Override
    protected void technicalCheck(Car car) {
        Log.log(source(), "Етап 4. Технічна перевірка: двигун, рівень оливи, гальма, вихлопна система");
    }
}
