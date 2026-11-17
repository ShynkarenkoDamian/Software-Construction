package carsharing.command;

import carsharing.model.Car;
import carsharing.util.Logger;

/** ConcreteCommand: увімкнути аварійну сигналізацію. */
public class HazardLightsCommand implements Command {
    private final Car car;
    private boolean before; // стан до виконання, щоб undo повернув його

    public HazardLightsCommand(Car car) {
        this.car = car;
    }

    @Override
    public void execute() {
        before = car.isHazardOn();
        Logger.log("HazardLightsCommand", "виконує для авто " + car.getPlate());
        car.setHazardOn(true);
    }

    @Override
    public void undo() {
        Logger.log("HazardLightsCommand", "скасовує для авто " + car.getPlate());
        car.setHazardOn(before);
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public String describe() {
        return "увімкнути аварійну сигналізацію (" + car.getPlate() + ")";
    }
}
