package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/**
 * ConcreteState: авто заброньоване. Бронювання діє 20 хвилин;
 * якщо клієнт за цей час не відкрив авто, бронювання скасовується і авто знову доступне.
 */
public class ReservedState implements CarState {

    public static final int RESERVATION_MINUTES = 20;

    private int waitedMinutes = 0;

    @Override
    public String title() {
        return "заброньоване";
    }

    @Override
    public void unlock(SharedCar car, String client) {
        if (!client.equals(car.getCurrentClient())) {
            car.refuse("відкриття", "авто заброньоване іншим клієнтом (" + car.getCurrentClient() + ")");
            return;
        }
        Log.log(car.source(), "Клієнт " + client + " відкрив авто, починається поїздка");
        car.setState(new InTripState());
    }

    @Override
    public void tick(SharedCar car, int minutes) {
        waitedMinutes += minutes;
        Log.log(car.source(), "Очікування клієнта " + car.getCurrentClient() + ": "
                + waitedMinutes + " з " + RESERVATION_MINUTES + " хв");
        if (waitedMinutes >= RESERVATION_MINUTES) {
            Log.log(car.source(), "Бронювання не підтверджене за " + RESERVATION_MINUTES + " хв — скасовано");
            car.setCurrentClient(null);
            car.setState(new AvailableState());
        }
    }
}
