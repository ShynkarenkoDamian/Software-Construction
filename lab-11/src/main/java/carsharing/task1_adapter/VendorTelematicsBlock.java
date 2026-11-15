package carsharing.task1_adapter;

import carsharing.common.Log;

/**
 * Блок телематики нового виробника (Adaptee). Код змінювати не можна.
 * Координати - градуси-хвилини-секунди, пальне - літри, інші назви методів.
 */
public class VendorTelematicsBlock {

    private final String plate;
    private final int[] latDms;
    private final int[] lonDms;
    private final double fuelLiters;

    public VendorTelematicsBlock(String plate, int[] latDms, int[] lonDms, double fuelLiters) {
        this.plate = plate;
        this.latDms = latDms;
        this.lonDms = lonDms;
        this.fuelLiters = fuelLiters;
    }

    private static String format(int[] dms) {
        return dms[0] + "°" + dms[1] + "'" + dms[2] + "\"";
    }

    public String readPlate() {
        Log.log("VendorTelematicsBlock", "readPlate() -> " + plate);
        return plate;
    }

    public int[] readLatDms() {
        Log.log("VendorTelematicsBlock", "readLatDms() -> " + format(latDms));
        return latDms;
    }

    public int[] readLonDms() {
        Log.log("VendorTelematicsBlock", "readLonDms() -> " + format(lonDms));
        return lonDms;
    }

    public double readFuelLiters() {
        Log.log("VendorTelematicsBlock", "readFuelLiters() -> " + fuelLiters + " л");
        return fuelLiters;
    }
}
