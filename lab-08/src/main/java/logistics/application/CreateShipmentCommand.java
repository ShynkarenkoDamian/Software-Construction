package logistics.application;

/** DTO: дані, які CLI передає контролеру. Жодної логіки. */
public record CreateShipmentCommand(
        String companyName,
        String contactPerson,
        String email,
        double weightKg,
        double volumeCubMetres,
        double declaredValue,
        String deliveryType) {
}
