package carsharing.task1_adapter;

import carsharing.common.Log;
import java.util.Locale;

/** Старий блок телематики: вже працює в потрібному форматі, тому реалізує Telematics напряму. */
public class LegacyTelematicsUnit implements Telematics {

    private final String carId;
    private final double latitude;
    private final double longitude;
    private final int fuelPercent;

    public LegacyTelematicsUnit(String carId, double latitude, double longitude, int fuelPercent) {
        this.carId = carId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fuelPercent = fuelPercent;
    }

    private void log(String event) {
        Log.log("LegacyTelematicsUnit", event);
    }

    @Override
    public String getCarId() {
        log("getCarId() -> " + carId);
        return carId;
    }

    @Override
    public double getLatitude() {
        log(String.format(Locale.ROOT, "getLatitude() -> %.6f (без перетворення)", latitude));
        return latitude;
    }

    @Override
    public double getLongitude() {
        log(String.format(Locale.ROOT, "getLongitude() -> %.6f (без перетворення)", longitude));
        return longitude;
    }

    @Override
    public int getFuelPercent() {
        log("getFuelPercent() -> " + fuelPercent + "% (без перетворення)");
        return fuelPercent;
    }
}
