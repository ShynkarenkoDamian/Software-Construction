package carsharing.task4_decorator;

/** Опція поїздки (ConcreteDecorator): поїздка за межі міста. */
public class OutOfCityOption extends TripOption {

    public OutOfCityOption(Trip wrapped) {
        super(wrapped, "поїздка за межі міста", 1.50);
    }
}
