package logistics.application;

import logistics.domain.DeliveryTariff;
import logistics.domain.ExpressTariff;
import logistics.domain.FreightTariff;
import logistics.domain.StandardTariff;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Pure Fabrication + Dictionary Dispatch: замість if-else за рядком типу доставки
 * шукаємо тариф у словнику. Новий тип доставки = один новий клас + один рядок тут.
 */
public class TariffRegistry {

    private final Map<String, DeliveryTariff> tariffs = new HashMap<>();

    public TariffRegistry() {
        register(new StandardTariff());
        register(new ExpressTariff());
        register(new FreightTariff());
    }

    private void register(DeliveryTariff tariff) {
        tariffs.put(tariff.name().toLowerCase(Locale.ROOT), tariff);
    }

    public DeliveryTariff find(String deliveryType) {
        if (deliveryType == null) {
            throw new IllegalArgumentException("Тип доставки не вказано");
        }
        DeliveryTariff tariff = tariffs.get(deliveryType.trim().toLowerCase(Locale.ROOT));
        if (tariff == null) {
            throw new IllegalArgumentException(
                    "Невідомий тип доставки: '" + deliveryType + "'. Доступні: Standard, Express, Freight");
        }
        return tariff;
    }
}
