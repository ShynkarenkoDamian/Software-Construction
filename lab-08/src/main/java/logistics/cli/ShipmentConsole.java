package logistics.cli;

import logistics.application.ShipmentController;

import java.util.Scanner;

/**
 * Thin Console UI: лише зчитує текст, передає його парсеру й контролеру
 * та друкує результат. Жодних обчислень.
 */
public class ShipmentConsole {

    private final Scanner scanner = new Scanner(System.in);
    private final ShipmentController controller;
    private final ConsoleCommandParser parser;

    public ShipmentConsole(ShipmentController controller, ConsoleCommandParser parser) {
        this.controller = controller;
        this.parser = parser;
    }

    public void run() {
        System.out.println("=== Логістична компанія ===");
        while (true) {
            System.out.println();
            System.out.println("1. Оформити відправку вантажу");
            System.out.println("0. Вихід");
            String choice = prompt("Ваш вибір: ");
            if (choice == null || "0".equals(choice.trim())) {
                System.out.println("До побачення!");
                return;
            }
            if ("1".equals(choice.trim())) {
                createShipment();
            } else {
                System.out.println("Невідомий пункт меню.");
            }
        }
    }

    private void createShipment() {
        System.out.println("=== Оформлення відправки вантажу ===");
        String company = prompt("Назва компанії-відправника: ");
        String contact = prompt("Контактна особа: ");
        String email = prompt("Email: ");
        String weight = prompt("Вага вантажу, кг: ");
        String volume = prompt("Об'єм вантажу, м3: ");
        String value = prompt("Заявлена вартість, грн: ");
        String type = prompt("Тип доставки (Standard/Express/Freight): ");
        if (type == null) {
            return;
        }
        try {
            String waybill = controller.registerShipment(
                    parser.toCommand(company, contact, email, weight, volume, value, type));
            System.out.println(waybill);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /** Повертає null, якщо введення закінчилось (Ctrl+D / кінець файлу). */
    private String prompt(String message) {
        System.out.print(message);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
}
