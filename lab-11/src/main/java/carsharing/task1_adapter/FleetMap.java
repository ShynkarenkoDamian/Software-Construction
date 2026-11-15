package carsharing.task1_adapter;

import carsharing.common.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Карта парку (Client): працює лише з інтерфейсом Telematics і не знає про блоки виробників. */
public class FleetMap {

    private final List<Telematics> cars = new ArrayList<>();

    public void addCar(Telematics telematics) {
        cars.add(telematics);
    }

    public void render() {
        Log.log("FleetMap", "відображення карти, авто: " + cars.size());
        for (Telematics t : cars) {
            String id = t.getCarId();
            double lat = t.getLatitude();
            double lon = t.getLongitude();
            int fuel = t.getFuelPercent();
            Log.log("FleetMap", String.format(Locale.ROOT, "авто %s: (%.5f; %.5f), пальне %d%%", id, lat, lon, fuel));
        }
    }
}
