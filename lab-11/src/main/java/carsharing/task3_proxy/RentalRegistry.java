package carsharing.task3_proxy;

import java.util.HashMap;
import java.util.Map;

/** Облік оренди: хто є поточним орендарем кожного авто. Допоміжний клас для перевірки в заступнику. */
public class RentalRegistry {

    private final Map<String, String> renters = new HashMap<>();

    public void rent(String carId, String clientId) {
        renters.put(carId, clientId);
    }

    public boolean isCurrentRenter(String carId, String clientId) {
        return clientId.equals(renters.get(carId));
    }
}
