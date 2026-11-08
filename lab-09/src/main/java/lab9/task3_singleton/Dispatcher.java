package lab9.task3_singleton;

import lab9.common.ConsoleLogger;

/** Диспетчерська: резервує авто для корпоративних клієнтів. Клієнт Singleton. */
public class Dispatcher {

    private static final String SOURCE = "Dispatcher";

    private final FleetMap fleetMap = FleetMap.getInstance();

    public boolean reserveForCorporateClient(String plateNumber, String company) {
        ConsoleLogger.log(SOURCE, "Резервую " + plateNumber + " для корпоративного клієнта " + company);
        return fleetMap.reserve(plateNumber, company);
    }

    /** Виводить у журнал ідентифікатор карти, з якою працює диспетчерська, і повертає його. */
    public String reportMapId() {
        ConsoleLogger.log(SOURCE, "Працюю з картою " + fleetMap.getId());
        return fleetMap.getId();
    }
}
