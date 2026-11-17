package carsharing.model;

/** Подія авто, яку отримують підписники. */
public record CarEvent(CarEventType type, String carPlate, String details) {
}
