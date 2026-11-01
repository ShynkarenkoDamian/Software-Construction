package logistics.application;

import logistics.domain.Shipment;

/**
 * Indirection + Protected Variations: застосунок знає лише цей інтерфейс,
 * а де саме зберігаються відправки (пам'ять, БД, файл) — деталь інфраструктури.
 */
public interface ShipmentRepository {

    void save(Shipment shipment);
}
