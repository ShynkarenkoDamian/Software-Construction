package logistics.application;

import logistics.domain.Shipment;

/**
 * Application Service: виконує кроки "відправити -> зберегти -> сповістити -> зробити накладну".
 * Винесено з контролера, щоб контролер лишався тонким і мав низьке зчеплення.
 * Тут немає ні консолі, ні розрахунків — лише координація.
 */
public class ShipmentDispatchService {

    private final ShipmentRepository repository;
    private final TrackingNotifier notifier;
    private final WaybillFormatter formatter;

    public ShipmentDispatchService(ShipmentRepository repository, TrackingNotifier notifier,
                                   WaybillFormatter formatter) {
        this.repository = repository;
        this.notifier = notifier;
        this.formatter = formatter;
    }

    /** Повертає текст накладної. */
    public String dispatch(Shipment shipment) {
        shipment.dispatch();
        repository.save(shipment);
        notifier.sendTrackingCode(shipment.senderEmail(), formatter.waybillNumber(shipment));
        return formatter.format(shipment);
    }
}
