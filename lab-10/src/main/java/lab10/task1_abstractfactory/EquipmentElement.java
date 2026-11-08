package lab10.task1_abstractfactory;

/** Спільна абстракція будь-якого елемента набору обладнання авто. */
public interface EquipmentElement {

    /** Тип авто, для якого призначено елемент. */
    VehicleType getVehicleType();

    /** Читабельний опис елемента. */
    String describe();
}
