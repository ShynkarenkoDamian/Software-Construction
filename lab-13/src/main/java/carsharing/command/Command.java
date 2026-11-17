package carsharing.command;

import carsharing.model.Car;

/** Command: віддалена команда авто, яка вміє виконатися і скасуватися. */
public interface Command {
    void execute();

    void undo();

    Car getCar();

    String describe();
}
