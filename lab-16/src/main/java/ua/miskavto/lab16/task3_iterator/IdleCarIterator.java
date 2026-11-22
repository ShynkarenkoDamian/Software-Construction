package ua.miskavto.lab16.task3_iterator;

/** ConcreteIterator для аналітики: авто, що простоюють понад задану кількість годин. */
public class IdleCarIterator extends FilteredCarIterator {

    private final int minIdleHours;

    public IdleCarIterator(Zone zone, int minIdleHours) {
        super(new ZoneCarIterator(zone));
        this.minIdleHours = minIdleHours;
    }

    @Override
    protected boolean accepts(ParkedCar car) {
        return car.getIdleHours() > minIdleHours;
    }
}
