package logistics.cli;

import logistics.application.CreateShipmentCommand;

/**
 * Pure Fabrication: перетворює текст, введений у консолі, на DTO-команду.
 * Лише розбір рядків у числа — жодної бізнес-логіки.
 */
public class ConsoleCommandParser {

    public CreateShipmentCommand toCommand(String company, String contact, String email,
                                           String weight, String volume, String declaredValue,
                                           String deliveryType) {
        return new CreateShipmentCommand(
                company.trim(),
                contact.trim(),
                email.trim(),
                parseNumber(weight, "Вага"),
                parseNumber(volume, "Об'єм"),
                parseNumber(declaredValue, "Заявлена вартість"),
                deliveryType.trim());
    }

    private double parseNumber(String text, String fieldName) {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " має бути числом");
        }
    }
}
