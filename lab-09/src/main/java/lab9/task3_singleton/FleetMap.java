package lab9.task3_singleton;

import lab9.common.ConsoleLogger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Єдина карта парку автомобілів (шаблон Singleton).
 * <p>
 * Правила:
 * <ul>
 *   <li>конструктор приватний — створити ще одну карту в обхід {@link #getInstance()} неможливо;</li>
 *   <li>єдиний екземпляр створюється ліниво, при першому зверненні (double-checked locking,
 *       поле {@code volatile}), тому повідомлення про ініціалізацію з'являється один раз;</li>
 *   <li>диспетчери й мобільний застосунок працюють з тим самим екземпляром, тому бачать
 *       один і той самий стан кожного авто;</li>
 *   <li>методи, що змінюють стан, синхронізовані: два одночасні запити на одне авто не пройдуть обидва.</li>
 * </ul>
 */
public final class FleetMap {

    private static final String SOURCE = "FleetMap";

    private static volatile FleetMap instance;

    private final Map<String, FleetCar> cars = new LinkedHashMap<>();

    private FleetMap() {
        addCar("CE1234AA", "Центр, вул. Головна");
        addCar("CE2345BB", "Північний район, вул. Руська");
        addCar("CE3456CC", "Аеропорт, паркінг P1");
        addCar("CE4567DD", "Університет, вул. Коцюбинського");
        ConsoleLogger.log(SOURCE, "Карту парку ініціалізовано");
    }

    /** Глобальна точка доступу до єдиної карти парку. */
    public static FleetMap getInstance() {
        FleetMap result = instance;
        if (result == null) {
            synchronized (FleetMap.class) {
                result = instance;
                if (result == null) {
                    result = new FleetMap();
                    instance = result;
                }
            }
        }
        return result;
    }

    private void addCar(String plateNumber, String location) {
        cars.put(plateNumber, new FleetCar(plateNumber, location));
    }

    /** Резервує авто для клієнта (викликає диспетчер). */
    public synchronized boolean reserve(String plateNumber, String clientName) {
        return occupy(plateNumber, CarStatus.RESERVED, clientName);
    }

    /** Видає авто клієнту (викликає мобільний застосунок). */
    public synchronized boolean issue(String plateNumber, String clientName) {
        return occupy(plateNumber, CarStatus.ISSUED, clientName);
    }

    private boolean occupy(String plateNumber, CarStatus newStatus, String clientName) {
        FleetCar car = cars.get(plateNumber);
        if (car == null) {
            ConsoleLogger.log(SOURCE, "Відмова: авто " + plateNumber + " відсутнє на карті парку");
            return false;
        }
        if (car.getStatus() != CarStatus.AVAILABLE) {
            ConsoleLogger.log(SOURCE, "Відмова: Авто " + plateNumber + " " + car.getStatus()
                    + " (" + car.getHolder() + ")");
            return false;
        }
        car.occupy(newStatus, clientName);
        String action = newStatus == CarStatus.RESERVED ? "зарезервовано для" : "видано клієнту";
        ConsoleLogger.log(SOURCE, "Авто " + plateNumber + " " + action + " " + clientName);
        return true;
    }

    /** Виводить у журнал стан усіх авто так, як його бачить вказаний споживач карти. */
    public synchronized void printFleet(String viewer) {
        for (FleetCar car : cars.values()) {
            String holder = car.getHolder() == null ? "" : " — " + car.getHolder();
            ConsoleLogger.log(viewer, "  " + car + " [" + car.getLocation() + "]: " + car.getStatus() + holder);
        }
    }

    /** Ідентифікатор екземпляра: однаковий для всіх, хто працює з картою. */
    public String getId() {
        return "FleetMap@" + Integer.toHexString(System.identityHashCode(this));
    }
}
