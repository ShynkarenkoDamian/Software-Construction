package carsharing.task3_bridge;

import java.util.Locale;

/** Звіт про поїздку для клієнта (RefinedAbstraction). */
public class TripReport extends Report {

    private final String clientName;
    private final String carId;
    private final int minutes;
    private final double cost;

    public TripReport(ReportChannel channel, String clientName, String carId, int minutes, double cost) {
        super(channel);
        this.clientName = clientName;
        this.carId = carId;
        this.minutes = minutes;
        this.cost = cost;
    }

    @Override
    protected String getTitle() {
        return "Звіт про поїздку";
    }

    @Override
    protected String buildBody() {
        return String.format(Locale.ROOT, "клієнт %s, авто %s, %d хв, вартість %.2f грн",
                clientName, carId, minutes, cost);
    }
}
