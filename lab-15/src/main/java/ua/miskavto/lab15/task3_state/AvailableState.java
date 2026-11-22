package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/** ConcreteState: авто доступне для бронювання. */
public class AvailableState implements CarState {

    @Override
    public String title() {
        return "доступне";
    }

    @Override
    public void reserve(SharedCar car, String client) {
        car.setCurrentClient(client);
        Log.log(car.source(), "Авто заброньовано клієнтом " + client);
        car.setState(new ReservedState());
    }

    @Override
    public void sendToMaintenance(SharedCar car) {
        Log.log(car.source(), "Авто відправлено на обслуговування");
        car.setState(new MaintenanceState());
    }
}
