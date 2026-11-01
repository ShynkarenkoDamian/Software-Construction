package logistics.infrastructure;

import logistics.application.ShipmentRepository;
import logistics.application.WaybillFormatter;
import logistics.domain.Shipment;

import java.util.HashMap;
import java.util.Map;

/** Реєстр відправок у пам'яті (імітація бази даних). */
public class InMemoryShipmentRepository implements ShipmentRepository {

    private final Map<Integer, Shipment> storage = new HashMap<>();
    private final WaybillFormatter formatter;

    public InMemoryShipmentRepository(WaybillFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void save(Shipment shipment) {
        storage.put(shipment.trackingId(), shipment);
        System.out.println("[Реєстр] Накладну " + formatter.waybillNumber(shipment)
                + " додано у базу відправок.");
    }
}
