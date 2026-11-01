package logistics.application;

import logistics.domain.Shipment;

import java.util.Locale;

/**
 * Pure Fabrication: формування тексту накладної — це не обов'язок доменної сутності.
 */
public class WaybillFormatter {

    public String waybillNumber(Shipment shipment) {
        return "TTN-" + shipment.trackingId();
    }

    public String format(Shipment shipment) {
        return String.format(Locale.ROOT,
                "Товарно-транспортна накладна %s%n"
                        + "Відправник: %s%n"
                        + "Тип: %s%n"
                        + "Транспортування: %.2f грн%n"
                        + "Страховка: %.2f грн%n"
                        + "Загальна вартість: %.2f грн",
                waybillNumber(shipment),
                shipment.senderCompanyName(),
                shipment.deliveryType(),
                shipment.transportFee(),
                shipment.insuranceFee(),
                shipment.totalCost());
    }
}
