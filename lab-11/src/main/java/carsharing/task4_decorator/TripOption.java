package carsharing.task4_decorator;

import carsharing.common.Log;
import java.util.Locale;

/**
 * Базовий Decorator: реалізує Trip і тримає обгорнуту поїздку (звичайну або вже з іншими опціями).
 * Конкретна опція задає лише назву й доплату за хвилину.
 */
public abstract class TripOption implements Trip {

    private final Trip wrapped;
    private final String name;
    private final double surcharge;

    protected TripOption(Trip wrapped, String name, double surcharge) {
        this.wrapped = wrapped;
        this.name = name;
        this.surcharge = surcharge;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + " + name;
    }

    @Override
    public double getRatePerMinute() {
        double inner = wrapped.getRatePerMinute();
        Log.log(getClass().getSimpleName(), String.format(Locale.ROOT,
                "опція «%s»: +%.2f грн/хв до %.2f", name, surcharge, inner));
        return inner + surcharge;
    }

    @Override
    public int getMinutes() {
        return wrapped.getMinutes();
    }
}
