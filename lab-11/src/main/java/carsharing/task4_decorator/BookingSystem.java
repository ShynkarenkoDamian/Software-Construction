package carsharing.task4_decorator;

import carsharing.common.Log;
import java.util.Locale;

/** Система оформлення (Client): працює з Trip і не знає, чи є на поїздці опції. */
public class BookingSystem {

    public void checkout(Trip trip) {
        Log.log("BookingSystem", "оформлення: " + trip.getDescription());
        double cost = trip.getCost();
        Log.log("BookingSystem", String.format(Locale.ROOT, "підсумкова вартість: %.2f грн", cost));
    }
}
