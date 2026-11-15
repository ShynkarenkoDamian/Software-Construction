package carsharing.task3_bridge;

import java.util.Locale;

/** Щомісячний звіт для корпоративного клієнта (RefinedAbstraction). */
public class MonthlyCorporateReport extends Report {

    private final String company;
    private final String month;
    private final int trips;
    private final double total;

    public MonthlyCorporateReport(ReportChannel channel, String company, String month, int trips, double total) {
        super(channel);
        this.company = company;
        this.month = month;
        this.trips = trips;
        this.total = total;
    }

    @Override
    protected String getTitle() {
        return "Щомісячний корпоративний звіт";
    }

    @Override
    protected String buildBody() {
        return String.format(Locale.ROOT, "%s, %s: поїздок %d, загальна сума %.2f грн", company, month, trips, total);
    }
}
