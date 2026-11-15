package carsharing.task1_adapter;

/**
 * Власний інтерфейс телематики карти парку (Target).
 * Координати - десяткові градуси, пальне - відсотки.
 */
public interface Telematics {

    String getCarId();

    double getLatitude();

    double getLongitude();

    int getFuelPercent();
}
