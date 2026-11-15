package carsharing.task3_proxy;

import carsharing.common.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Proxy (захисний + журналюючий): має той самий інтерфейс, що й сервер виробника.
 * Перевіряє орендаря ДО звернення до сервера, веде журнал команд і лише потім делегує виклик.
 */
public class CarControlProxy implements CarControl {

    private final String carId;
    private final CarControl server;
    private final RentalRegistry rentals;
    private final List<String> journal = new ArrayList<>();

    public CarControlProxy(String carId, CarControl server, RentalRegistry rentals) {
        this.carId = carId;
        this.server = server;
        this.rentals = rentals;
    }

    @Override
    public void openDoors(String clientId) {
        if (allowed("відкрити двері", clientId)) {
            server.openDoors(clientId);
        }
    }

    @Override
    public void startEngine(String clientId) {
        if (allowed("увімкнути двигун", clientId)) {
            server.startEngine(clientId);
        }
    }

    @Override
    public void honk(String clientId) {
        if (allowed("посигналити", clientId)) {
            server.honk(clientId);
        }
    }

    public List<String> getJournal() {
        return journal;
    }

    private boolean allowed(String command, String clientId) {
        boolean ok = rentals.isCurrentRenter(carId, clientId);
        journal.add(clientId + ": " + command + " (" + carId + ") - " + (ok ? "дозволено" : "відмовлено"));
        if (ok) {
            Log.log("CarControlProxy", "клієнт " + clientId + " є орендарем " + carId
                    + ", команда «" + command + "» передається на сервер");
        } else {
            Log.log("CarControlProxy", "ВІДМОВА для " + clientId + ": Ви не є орендарем цього авто"
                    + " (команда «" + command + "», сервер НЕ викликається)");
        }
        return ok;
    }
}
