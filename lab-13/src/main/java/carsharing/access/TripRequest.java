package carsharing.access;

import carsharing.model.Customer;

/** Запит, який рухається по ланцюгу: клієнт хоче розпочати поїздку на конкретному авто. */
public class TripRequest {
    private final Customer customer;
    private final String carPlate;
    private final CarClass carClass;
    private final int requiredAmount; // сума, яка має бути на картці, грн

    public TripRequest(Customer customer, String carPlate, CarClass carClass, int requiredAmount) {
        this.customer = customer;
        this.carPlate = carPlate;
        this.carClass = carClass;
        this.requiredAmount = requiredAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public String describe() {
        return customer.getName() + " -> " + carClass.getLabel() + " " + carPlate;
    }
}
