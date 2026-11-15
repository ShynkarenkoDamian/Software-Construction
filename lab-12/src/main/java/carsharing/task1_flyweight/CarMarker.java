package carsharing.task1_flyweight;

import java.util.Locale;

/**
 * Context: позначка на мапі. Зберігає лише власні (зовнішні) дані
 * і посилання на спільний об'єкт CarModel.
 */
public class CarMarker {

    private final String plate;
    private final double latitude;
    private final double longitude;
    private final String time;
    private final CarModel model;

    public CarMarker(String plate, double latitude, double longitude, String time, CarModel model) {
        this.plate = plate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.model = model;
    }

    public CarModel getModel() {
        return model;
    }

    public String describe() {
        return String.format(Locale.ROOT, "позначка авто %s (%.5f; %.5f) о %s, модель %s [іконка %s, %s]",
                plate, latitude, longitude, time, model.getName(), model.getIcon(), model.getCharacteristics());
    }
}
