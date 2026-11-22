package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/** ConcreteState: авто на обслуговуванні. */
public class MaintenanceState implements CarState {

    @Override
    public String title() {
        return "на обслуговуванні";
    }

    @Override
    public void reserve(SharedCar car, String client) {
        car.refuse("бронювання", "авто на обслуговуванні, бронювання можливе після його завершення");
    }

    @Override
    public void returnFromMaintenance(SharedCar car) {
        Log.log(car.source(), "Обслуговування завершено");
        car.setState(new AvailableState());
    }
}
