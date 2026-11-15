package carsharing.task2_composite;

import carsharing.common.Log;

/**
 * Client: аналітика. Працює лише з ZoneComponent і не знає, що це
 * (місто, район чи майданчик) та яка в нього глибина вкладеності.
 */
public class ZoneAnalytics {

    public void printSummary(ZoneComponent zone) {
        Log.log("ZoneAnalytics", "підсумок для «" + zone.getName() + "»: авто "
                + zone.getCarCount() + ", виручка " + zone.getRevenue() + " грн");
    }
}
