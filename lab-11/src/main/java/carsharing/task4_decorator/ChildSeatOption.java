package carsharing.task4_decorator;

/** Опція поїздки (ConcreteDecorator): дитяче крісло. */
public class ChildSeatOption extends TripOption {

    public ChildSeatOption(Trip wrapped) {
        super(wrapped, "дитяче крісло", 0.50);
    }
}
