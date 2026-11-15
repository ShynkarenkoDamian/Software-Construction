package carsharing.task4_decorator;

/** Опція поїздки (ConcreteDecorator): багажник на дах. */
public class RoofRackOption extends TripOption {

    public RoofRackOption(Trip wrapped) {
        super(wrapped, "багажник на дах", 0.70);
    }
}
