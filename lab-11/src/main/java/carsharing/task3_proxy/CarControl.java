package carsharing.task3_proxy;

/**
 * Спільний інтерфейс керування авто (Subject).
 * Його реалізують і реальний сервер виробника, і заступник, тому застосунок не бачить різниці.
 */
public interface CarControl {

    void openDoors(String clientId);

    void startEngine(String clientId);

    void honk(String clientId);
}
