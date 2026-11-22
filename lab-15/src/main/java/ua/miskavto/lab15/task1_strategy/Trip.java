package ua.miskavto.lab15.task1_strategy;

import ua.miskavto.lab15.common.Log;

/**
 * Context: поїздка. Не знає, як саме рахується вартість — делегує тарифу клієнта.
 * Коефіцієнт години пік застосовується до суми тарифу і не залежить від його типу.
 */
public class Trip {

    private final Client client;
    private final int minutes;
    private final double km;
    private final double peakCoefficient;

    public Trip(Client client, int minutes, double km) {
        this(client, minutes, km, 1.0);
    }

    public Trip(Client client, int minutes, double km, double peakCoefficient) {
        this.client = client;
        this.minutes = minutes;
        this.km = km;
        this.peakCoefficient = peakCoefficient;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getKm() {
        return km;
    }

    public double calculateCost() {
        Tariff tariff = client.getTariff();
        double base = tariff.calculate(this);
        double total = base * peakCoefficient;
        Log.log("Trip", client.getName() + ", " + minutes + " хв / " + km + " км, тариф «" + tariff.name()
                + "»: базова сума " + Log.money(base) + ", коефіцієнт години пік x" + peakCoefficient
                + " -> до сплати " + Log.money(total));
        return total;
    }
}
