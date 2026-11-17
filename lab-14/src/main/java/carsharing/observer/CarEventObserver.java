package carsharing.observer;

import carsharing.model.CarEvent;

/** Observer: усі, хто хоче отримувати події авто. */
public interface CarEventObserver {
    void onEvent(CarEvent event);
}
