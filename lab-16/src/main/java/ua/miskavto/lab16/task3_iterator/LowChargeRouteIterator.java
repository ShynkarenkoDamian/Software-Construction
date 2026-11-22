package ua.miskavto.lab16.task3_iterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/** ConcreteIterator для служби обслуговування: авто з низьким зарядом у порядку маршруту. */
public class LowChargeRouteIterator extends FilteredCarIterator {

    private final int maxChargePercent;

    public LowChargeRouteIterator(Zone zone, int maxChargePercent) {
        super(carsInRouteOrder(zone));
        this.maxChargePercent = maxChargePercent;
    }

    @Override
    protected boolean accepts(ParkedCar car) {
        return car.getChargePercent() < maxChargePercent;
    }

    private static Iterator<ParkedCar> carsInRouteOrder(Zone zone) {
        List<ParkingLot> lots = new ArrayList<>();
        zone.collectLots(lots);
        lots.sort(Comparator.comparingInt(ParkingLot::getRouteOrder));
        return lots.stream().flatMap(lot -> lot.cars().stream()).iterator();
    }
}
