package carsharing.task3_proxy;

/** Застосунок (Client): працює лише з інтерфейсом CarControl і не знає про перевірки. */
public class CarApp {

    private final CarControl car;

    public CarApp(CarControl car) {
        this.car = car;
    }

    public void openDoors(String clientId) {
        car.openDoors(clientId);
    }
}
