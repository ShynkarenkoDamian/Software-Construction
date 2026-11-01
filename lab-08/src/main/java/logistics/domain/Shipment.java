package logistics.domain;

/**
 * Вантажна відправка — корінь агрегату (Aggregate Root).
 *
 * Creator: Shipment створюється лише через статичний фабричний метод create(...),
 * який гарантує валідний стан (вартість уже розрахована, статус CREATED).
 * Information Expert: Shipment сам керує своїм статусом.
 * Закон Деметри: назовні віддаються senderEmail(), deliveryType() тощо,
 * щоб не писати shipment.sender().email().
 */
public final class Shipment {

    private final int trackingId;
    private final Sender sender;
    private final Cargo cargo;
    private final double transportFee;
    private final double insuranceFee;
    private final double totalCost;
    private ShipmentStatus status;

    private Shipment(int trackingId, Sender sender, Cargo cargo, PricingPolicy pricing) {
        this.trackingId = trackingId;
        this.sender = sender;
        this.cargo = cargo;
        this.transportFee = cargo.transportFee(pricing);
        this.insuranceFee = cargo.insuranceFee(pricing);
        this.totalCost = cargo.totalCost(pricing);
        this.status = ShipmentStatus.CREATED;
    }

    /** Static Factory Method (Creator). */
    public static Shipment create(int trackingId, Sender sender, Cargo cargo, PricingPolicy pricing) {
        if (sender == null || cargo == null || pricing == null) {
            throw new IllegalArgumentException("Відправник, вантаж і тарифна політика обов'язкові");
        }
        return new Shipment(trackingId, sender, cargo, pricing);
    }

    /** Бізнес-операція: відправити вантаж. Дозволено лише зі статусу CREATED. */
    public void dispatch() {
        if (status != ShipmentStatus.CREATED) {
            throw new IllegalStateException("Відправку вже відправлено");
        }
        status = ShipmentStatus.DISPATCHED;
    }

    public int trackingId() {
        return trackingId;
    }

    public double transportFee() {
        return transportFee;
    }

    public double insuranceFee() {
        return insuranceFee;
    }

    public double totalCost() {
        return totalCost;
    }

    public ShipmentStatus status() {
        return status;
    }

    // Делегування замість ланцюжків shipment.sender().email() (Закон Деметри)

    public String senderCompanyName() {
        return sender.companyName();
    }

    public String senderEmail() {
        return sender.email();
    }

    public String deliveryType() {
        return cargo.deliveryType();
    }
}
