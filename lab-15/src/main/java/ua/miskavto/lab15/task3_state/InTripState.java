package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/** ConcreteState: авто в поїздці. */
public class InTripState implements CarState {

    @Override
    public String title() {
        return "в поїздці";
    }

    @Override
    public void pause(SharedCar car) {
        Log.log(car.source(), "Клієнт " + car.getCurrentClient() + " залишив авто з продовженням оренди");
        car.setState(new PausedState());
    }

    @Override
    public void endTrip(SharedCar car) {
        Log.log(car.source(), "Поїздку клієнта " + car.getCurrentClient() + " завершено");
        car.setCurrentClient(null);
        car.setState(new AvailableState());
    }
}
