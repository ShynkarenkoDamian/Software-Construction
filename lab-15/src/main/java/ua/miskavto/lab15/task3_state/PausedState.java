package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/** ConcreteState: авто на паузі (клієнт залишив авто з продовженням оренди). */
public class PausedState implements CarState {

    @Override
    public String title() {
        return "на паузі";
    }

    @Override
    public void unlock(SharedCar car, String client) {
        if (!client.equals(car.getCurrentClient())) {
            car.refuse("відкриття", "авто на паузі в оренді іншого клієнта (" + car.getCurrentClient() + ")");
            return;
        }
        Log.log(car.source(), "Клієнт " + client + " відкрив авто, поїздка продовжується");
        car.setState(new InTripState());
    }

    @Override
    public void endTrip(SharedCar car) {
        car.refuse("завершення поїздки", "спершу відкрийте авто й поверніться в поїздку");
    }
}
