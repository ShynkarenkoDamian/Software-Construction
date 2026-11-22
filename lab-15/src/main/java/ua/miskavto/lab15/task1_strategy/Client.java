package ua.miskavto.lab15.task1_strategy;

import ua.miskavto.lab15.common.Log;

/** Клієнт каршерінгу. Тариф можна обрати й змінити під час виконання програми. */
public class Client {

    private final String name;
    private Tariff tariff;

    public Client(String name, Tariff tariff) {
        this.name = name;
        this.tariff = tariff;
        Log.log("Client", name + ": обрано тариф «" + tariff.name() + "»");
    }

    public String getName() {
        return name;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff newTariff) {
        Log.log("Client", name + ": тариф змінено «" + tariff.name() + "» → «" + newTariff.name() + "»");
        this.tariff = newTariff;
    }
}
