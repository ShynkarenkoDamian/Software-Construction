package lab9.task3_singleton;

import lab9.common.ConsoleLogger;

/** Мобільний застосунок: видає авто приватним клієнтам. Клієнт Singleton. */
public class MobileApp {

    private static final String SOURCE = "MobileApp";

    private final FleetMap fleetMap = FleetMap.getInstance();

    public boolean issueToPrivateClient(String plateNumber, String clientName) {
        ConsoleLogger.log(SOURCE, "Клієнт " + clientName + " просить видати " + plateNumber);
        return fleetMap.issue(plateNumber, clientName);
    }

    /** Виводить у журнал ідентифікатор карти, з якою працює застосунок, і повертає його. */
    public String reportMapId() {
        ConsoleLogger.log(SOURCE, "Працюю з картою " + fleetMap.getId());
        return fleetMap.getId();
    }

    /** Виводить стан парку так, як його бачить застосунок. */
    public void showFleet() {
        ConsoleLogger.log(SOURCE, "Стан парку на карті:");
        fleetMap.printFleet(SOURCE);
    }
}
