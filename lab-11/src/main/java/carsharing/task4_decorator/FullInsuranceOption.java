package carsharing.task4_decorator;

/** Опція поїздки (ConcreteDecorator): повне страхування. */
public class FullInsuranceOption extends TripOption {

    public FullInsuranceOption(Trip wrapped) {
        super(wrapped, "повне страхування", 1.00);
    }
}
