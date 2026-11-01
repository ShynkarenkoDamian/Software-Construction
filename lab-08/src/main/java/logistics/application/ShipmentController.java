package logistics.application;

/**
 * GRASP Controller (Use-Case Controller): перший об'єкт після CLI.
 * Приймає команду, просить фабрику створити відправку, передає її на оформлення.
 * Не містить ні консольного вводу/виводу, ні бізнес-розрахунків.
 */
public class ShipmentController {

    private final ShipmentFactory factory;
    private final ShipmentDispatchService dispatchService;

    public ShipmentController(ShipmentFactory factory, ShipmentDispatchService dispatchService) {
        this.factory = factory;
        this.dispatchService = dispatchService;
    }

    /** Повертає текст накладної для показу користувачу. */
    public String registerShipment(CreateShipmentCommand command) {
        return dispatchService.dispatch(factory.create(command));
    }
}
