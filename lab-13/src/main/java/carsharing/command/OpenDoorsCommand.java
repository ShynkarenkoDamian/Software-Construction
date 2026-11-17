package carsharing.command;

import carsharing.model.Car;
import carsharing.util.Logger;

/** ConcreteCommand: відкрити двері. */
public class OpenDoorsCommand implements Command {
    private final Car car;
    private boolean before; // стан до виконання, щоб undo повернув його

    public OpenDoorsCommand(Car car) {
        this.car = car;
    }

    @Override
    public void execute() {
        before = car.isDoorsOpen();
        Logger.log("OpenDoorsCommand", "виконує для авто " + car.getPlate());
        car.setDoorsOpen(true);
    }

    @Override
    public void undo() {
        Logger.log("OpenDoorsCommand", "скасовує для авто " + car.getPlate());
        car.setDoorsOpen(before);
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public String describe() {
        return "відкрити двері (" + car.getPlate() + ")";
    }
}
