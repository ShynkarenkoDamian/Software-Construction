package ua.miskavto.lab16.task3_iterator;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ConcreteIterator: усі авто зони, включно з вкладеними. Обхід у глибину: спершу майданчики зони,
 * потім вкладені зони. Власний стан (стек зон і поточні позиції) дає незалежні одночасні обходи.
 */
public class ZoneCarIterator implements Iterator<ParkedCar> {

    private final Deque<Zone> pendingZones = new ArrayDeque<>();
    private Iterator<ParkingLot> lots = Collections.emptyIterator();
    private Iterator<ParkedCar> cars = Collections.emptyIterator();

    public ZoneCarIterator(Zone zone) {
        pendingZones.push(zone);
    }

    @Override
    public boolean hasNext() {
        while (!cars.hasNext()) {
            if (lots.hasNext()) {
                cars = lots.next().cars().iterator();
            } else if (!pendingZones.isEmpty()) {
                Zone zone = pendingZones.pop();
                lots = zone.lots().iterator();
                for (int i = zone.subZones().size() - 1; i >= 0; i--) {
                    pendingZones.push(zone.subZones().get(i));
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public ParkedCar next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return cars.next();
    }
}
