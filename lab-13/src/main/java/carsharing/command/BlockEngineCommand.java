package carsharing.command;

import carsharing.model.Car;
import carsharing.util.Logger;

/** ConcreteCommand: заблокувати двигун. */
public class BlockEngineCommand implements Command {
    private final Car car;
    private boolean before; // стан до виконання, щоб undo повернув його

    public BlockEngineCommand(Car car) {
        this.car = car;
    }

    @Override
    public void execute() {
        before = car.isEngineBlocked();
        Logger.log("BlockEngineCommand", "виконує для авто " + car.getPlate());
        car.setEngineBlocked(true);
    }

    @Override
    public void undo() {
        Logger.log("BlockEngineCommand", "скасовує для авто " + car.getPlate());
        car.setEngineBlocked(before);
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public String describe() {
        return "заблокувати двигун (" + car.getPlate() + ")";
    }
}
