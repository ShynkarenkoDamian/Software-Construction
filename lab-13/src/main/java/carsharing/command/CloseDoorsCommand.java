package carsharing.command;

import carsharing.model.Car;
import carsharing.util.Logger;

/** ConcreteCommand: закрити двері. */
public class CloseDoorsCommand implements Command {
    private final Car car;
    private boolean before; // стан до виконання, щоб undo повернув його

    public CloseDoorsCommand(Car car) {
        this.car = car;
    }

    @Override
    public void execute() {
        before = car.isDoorsOpen();
        Logger.log("CloseDoorsCommand", "виконує для авто " + car.getPlate());
        car.setDoorsOpen(false);
    }

    @Override
    public void undo() {
        Logger.log("CloseDoorsCommand", "скасовує для авто " + car.getPlate());
        car.setDoorsOpen(before);
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public String describe() {
        return "закрити двері (" + car.getPlate() + ")";
    }
}
