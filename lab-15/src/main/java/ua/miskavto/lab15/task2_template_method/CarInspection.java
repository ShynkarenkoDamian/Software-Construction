package ua.miskavto.lab15.task2_template_method;

import ua.miskavto.lab15.common.Log;

/**
 * AbstractClass (Template Method): огляд авто перед поверненням у парк.
 * Послідовність етапів описана ТІЛЬКИ тут у final-методі inspect() і не може бути змінена підкласами.
 *
 * Етапи:
 *  1. зовнішній огляд (спільний)
 *  2. перевірка салону (спільна)
 *  -  мийка (необов'язковий етап, hook: за замовчуванням не потрібна)
 *  3. перевірка рівня енергії (різна для типів)
 *  4. технічна перевірка (різна для типів)
 *  5. повернення в доступні (спільний); якщо енергії мало — замість цього авто йде поповнювати енергію
 */
public abstract class CarInspection {

    public final void inspect(Car car) {
        Log.log(source(), "Початок огляду: " + car);
        exteriorInspection(car);
        interiorCheck(car);
        wash(car);
        boolean energyOk = checkEnergy(car);
        technicalCheck(car);
        if (energyOk) {
            returnToAvailable(car);
        } else {
            sendForEnergy(car);
        }
        Log.log(source(), "Огляд завершено: " + car);
    }

    // ---- спільні етапи (однакові для всіх типів, змінити в підкласі не можна) ----

    private void exteriorInspection(Car car) {
        Log.log(source(), "Етап 1. Зовнішній огляд: кузов, скло, колеса, номерний знак");
    }

    private void interiorCheck(Car car) {
        Log.log(source(), "Етап 2. Перевірка салону: чистота, речі клієнта, документи");
    }

    private void returnToAvailable(Car car) {
        car.setStatus(CarStatus.AVAILABLE);
        Log.log(source(), "Етап 5. Авто повернено в доступні");
    }

    // ---- hook-методи (мають поведінку за замовчуванням, можуть перевизначатися) ----

    /** Необов'язковий етап. */
    protected void wash(Car car) {
        Log.log(source(), "(необов'язковий етап) Мийка не потрібна — пропущено");
    }

    /** Що робити, коли енергії недостатньо. За замовчуванням — відправити на поповнення енергії. */
    protected void sendForEnergy(Car car) {
        car.setStatus(CarStatus.SENT_FOR_ENERGY);
        Log.log(source(), "Етап 5. Енергії недостатньо — авто відправлено на поповнення енергії замість повернення");
    }

    // ---- абстрактні етапи (кожен тип визначає сам) ----

    /** @return true, якщо рівня енергії достатньо для повернення в парк */
    protected abstract boolean checkEnergy(Car car);

    protected abstract void technicalCheck(Car car);

    protected String source() {
        return getClass().getSimpleName();
    }
}
