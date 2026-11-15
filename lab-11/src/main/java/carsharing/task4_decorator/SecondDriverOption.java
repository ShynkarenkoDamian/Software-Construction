package carsharing.task4_decorator;

/** Опція поїздки (ConcreteDecorator): другий водій. */
public class SecondDriverOption extends TripOption {

    public SecondDriverOption(Trip wrapped) {
        super(wrapped, "другий водій", 0.80);
    }
}
