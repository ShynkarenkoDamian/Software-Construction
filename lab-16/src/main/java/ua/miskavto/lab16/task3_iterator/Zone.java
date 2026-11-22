package ua.miskavto.lab16.task3_iterator;

import ua.miskavto.lab16.common.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ConcreteAggregate: зона міста. Містить майданчики та вкладені зони, а обхід авто віддає ітераторам.
 * Клієнти отримують лише Iterator і не знають, як зони та майданчики влаштовані всередині.
 */
public class Zone implements Iterable<ParkedCar> {

    private final String name;
    private final List<ParkingLot> lots = new ArrayList<>();
    private final List<Zone> subZones = new ArrayList<>();

    public Zone(String name) {
        this.name = name;
    }

    public void addLot(ParkingLot lot) {
        lots.add(lot);
    }

    public void addSubZone(Zone zone) {
        subZones.add(zone);
    }

    public String getName() {
        return name;
    }

    /** Усі авто зони, включно з вкладеними зонами. */
    @Override
    public Iterator<ParkedCar> iterator() {
        return new ZoneCarIterator(this);
    }

    /** Авто із зарядом нижче порогу в порядку маршруту (за номером зупинки майданчика). */
    public Iterator<ParkedCar> lowChargeRouteIterator(int maxChargePercent) {
        return new LowChargeRouteIterator(this, maxChargePercent);
    }

    /** Авто, що простоюють понад вказану кількість годин. */
    public Iterator<ParkedCar> idleIterator(int minIdleHours) {
        return new IdleCarIterator(this, minIdleHours);
    }

    /** Структура відкрита лише ітераторам цього пакета. */
    List<ParkingLot> lots() {
        return lots;
    }

    List<Zone> subZones() {
        return subZones;
    }

    /** Збирає майданчики зони й усіх вкладених зон. */
    void collectLots(List<ParkingLot> target) {
        target.addAll(lots);
        for (Zone zone : subZones) {
            zone.collectLots(target);
        }
    }

    public void logState(String stage) {
        Log.log("Zones", "Стан колекції " + stage + ":");
        logTree(0);
    }

    private void logTree(int depth) {
        String indent = "   " + "  ".repeat(depth);
        Log.log("Zones", indent + "Зона «" + name + "»");
        for (ParkingLot lot : lots) {
            String cars = lot.cars().stream().map(ParkedCar::toString).collect(Collectors.joining(", "));
            Log.log("Zones", indent + "  Майданчик «" + lot.getName() + "» (маршрут " + lot.getRouteOrder() + "): " + cars);
        }
        for (Zone zone : subZones) {
            zone.logTree(depth + 1);
        }
    }
}
