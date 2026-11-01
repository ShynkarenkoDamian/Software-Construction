package logistics.application;

import logistics.domain.Cargo;
import logistics.domain.PricingPolicy;
import logistics.domain.Sender;
import logistics.domain.Shipment;

/**
 * Creator / Pure Fabrication: збирає агрегат Shipment із даних команди.
 * Уся "збірка" в одному місці, а сутності лишаються чистими.
 */
public class ShipmentFactory {

    private final TariffRegistry tariffs;
    private final PricingPolicy pricing;
    private final IdSequence trackingIds;
    private final IdSequence senderIds;

    public ShipmentFactory(TariffRegistry tariffs, PricingPolicy pricing,
                           IdSequence trackingIds, IdSequence senderIds) {
        this.tariffs = tariffs;
        this.pricing = pricing;
        this.trackingIds = trackingIds;
        this.senderIds = senderIds;
    }

    public Shipment create(CreateShipmentCommand command) {
        Cargo cargo = new Cargo(command.weightKg(), command.volumeCubMetres(),
                command.declaredValue(), tariffs.find(command.deliveryType()));
        Sender sender = new Sender(senderIds.next(), command.companyName(),
                command.contactPerson(), command.email());
        return Shipment.create(trackingIds.next(), sender, cargo, pricing);
    }
}
