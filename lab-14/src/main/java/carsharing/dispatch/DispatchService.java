package carsharing.dispatch;

/** Colleague: базова служба. Знає лише посередника, а не інші служби. */
public abstract class DispatchService {
    protected final DispatchMediator mediator;
    private final String name;

    protected DispatchService(DispatchMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
