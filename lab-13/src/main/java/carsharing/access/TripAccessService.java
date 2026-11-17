package carsharing.access;

import carsharing.util.Logger;

import java.util.EnumMap;
import java.util.Map;

/**
 * Точка входу в ланцюг: тримає окремий ланцюг перевірок для кожного класу авто.
 * Склад і порядок ланок задаються під час виконання методом configure().
 */
public class TripAccessService {
    private final Map<CarClass, AccessCheck> chains = new EnumMap<>(CarClass.class);

    /** Збирає ланцюг із переданих перевірок у вказаному порядку. Кожна ланка — окремий екземпляр. */
    public void configure(CarClass carClass, AccessCheck... checks) {
        StringBuilder order = new StringBuilder();
        for (int i = 0; i < checks.length; i++) {
            checks[i].setNext(i + 1 < checks.length ? checks[i + 1] : null);
            if (i > 0) {
                order.append(" -> ");
            }
            order.append(checks[i].getClass().getSimpleName());
        }
        chains.put(carClass, checks.length == 0 ? null : checks[0]);
        Logger.log("TripAccessService", "налаштовано ланцюг для (" + carClass.getLabel() + "): " + order);
    }

    public AccessResult requestAccess(TripRequest request) {
        Logger.log("TripAccessService", "отримав запит: " + request.describe());
        AccessCheck first = chains.get(request.getCarClass());
        if (first == null) {
            Logger.log("TripAccessService", "для класу авто не налаштовано жодної перевірки");
            return AccessResult.denied("TripAccessService", "ланцюг не налаштовано");
        }
        Logger.log("TripAccessService", "передає першій ланці: " + first.getClass().getSimpleName());
        AccessResult result = first.handle(request);
        if (result.granted()) {
            Logger.log("TripAccessService", "ДОПУСК НАДАНО клієнту " + request.getCustomer().getName()
                    + ": усі перевірки пройдено (остання ланка " + result.stoppedBy() + ")");
        } else {
            Logger.log("TripAccessService", "ВІДМОВА клієнту " + request.getCustomer().getName()
                    + ": зупинено ланкою " + result.stoppedBy() + " — " + result.reason());
        }
        return result;
    }
}
