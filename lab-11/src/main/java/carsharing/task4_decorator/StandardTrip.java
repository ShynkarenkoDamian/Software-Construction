package carsharing.task4_decorator;

import carsharing.common.Log;
import java.util.Locale;

/** Звичайна поїздка (ConcreteComponent): базовий тариф без опцій. */
public class StandardTrip implements Trip {

    private static final double BASE_RATE = 4.00;

    private final String carId;
    private final int minutes;

    public StandardTrip(String carId, int minutes) {
        this.carId = carId;
        this.minutes = minutes;
    }

    @Override
    public String getDescription() {
        return "Поїздка " + carId + " (" + minutes + " хв)";
    }

    @Override
    public double getRatePerMinute() {
        Log.log("StandardTrip", String.format(Locale.ROOT, "базовий тариф %.2f грн/хв", BASE_RATE));
        return BASE_RATE;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }
}
