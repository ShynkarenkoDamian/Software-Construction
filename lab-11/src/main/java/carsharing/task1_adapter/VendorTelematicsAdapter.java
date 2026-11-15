package carsharing.task1_adapter;

import carsharing.common.Log;
import java.util.Locale;

/**
 * Adapter: єдине місце, де назви методів і одиниці вимірювання
 * блоку виробника перетворюються на формат Telematics.
 */
public class VendorTelematicsAdapter implements Telematics {

    private final VendorTelematicsBlock block;
    private final double tankCapacityLiters;

    public VendorTelematicsAdapter(VendorTelematicsBlock block, double tankCapacityLiters) {
        this.block = block;
        this.tankCapacityLiters = tankCapacityLiters;
    }

    private static double dmsToDecimal(int[] dms) {
        return dms[0] + dms[1] / 60.0 + dms[2] / 3600.0;
    }

    private void log(String event) {
        Log.log("VendorTelematicsAdapter", event);
    }

    @Override
    public String getCarId() {
        log("getCarId() -> делегую блоку: readPlate()");
        return block.readPlate();
    }

    @Override
    public double getLatitude() {
        log("getLatitude() -> делегую блоку: readLatDms()");
        double result = dmsToDecimal(block.readLatDms());
        log(String.format(Locale.ROOT, "перетворено ГМС -> десяткові градуси: %.6f", result));
        return result;
    }

    @Override
    public double getLongitude() {
        log("getLongitude() -> делегую блоку: readLonDms()");
        double result = dmsToDecimal(block.readLonDms());
        log(String.format(Locale.ROOT, "перетворено ГМС -> десяткові градуси: %.6f", result));
        return result;
    }

    @Override
    public int getFuelPercent() {
        log("getFuelPercent() -> делегую блоку: readFuelLiters()");
        double liters = block.readFuelLiters();
        int percent = (int) Math.round(liters / tankCapacityLiters * 100);
        log(String.format(Locale.ROOT, "перетворено літри -> відсотки: %.1f л із %.1f л = %d%%",
                liters, tankCapacityLiters, percent));
        return percent;
    }
}
