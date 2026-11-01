package logistics.app;

import logistics.application.IdSequence;
import logistics.application.ShipmentController;
import logistics.application.ShipmentDispatchService;
import logistics.application.ShipmentFactory;
import logistics.application.TariffRegistry;
import logistics.application.WaybillFormatter;
import logistics.cli.ConsoleCommandParser;
import logistics.cli.ShipmentConsole;
import logistics.domain.DefaultPricingPolicy;
import logistics.infrastructure.ConsoleEmailNotifier;
import logistics.infrastructure.InMemoryShipmentRepository;

/**
 * Composition Root: єдине місце, де конкретні класи "зшиваються" між собою.
 * Решта коду залежить лише від інтерфейсів.
 */
public class Main {

    public static void main(String[] args) {
        WaybillFormatter formatter = new WaybillFormatter();
        ShipmentFactory factory = new ShipmentFactory(
                new TariffRegistry(),
                new DefaultPricingPolicy(),
                new IdSequence(770099),   // номер накладної з legacy-коду
                new IdSequence(55));      // id відправника з legacy-коду
        ShipmentDispatchService dispatchService = new ShipmentDispatchService(
                new InMemoryShipmentRepository(formatter),
                new ConsoleEmailNotifier(),
                formatter);
        ShipmentController controller = new ShipmentController(factory, dispatchService);

        new ShipmentConsole(controller, new ConsoleCommandParser()).run();
    }
}
