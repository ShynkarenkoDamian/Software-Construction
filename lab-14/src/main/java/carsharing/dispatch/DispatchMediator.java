package carsharing.dispatch;

/** Mediator: єдина точка, через яку служби повідомляють про свої події. */
public interface DispatchMediator {
    void reportEvent(DispatchService sender, DispatchEventType type, String carPlate);
}
