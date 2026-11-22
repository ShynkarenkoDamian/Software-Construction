package ua.miskavto.lab16.task1_visitor;

/** ConcreteElement: гібрид. */
public class HybridCar extends Vehicle {

    public HybridCar(String plate, double value, int mileageKm, int monthlyKm) {
        super(plate, value, mileageKm, monthlyKm);
    }

    @Override
    public String typeName() {
        return "гібрид";
    }

    @Override
    public void accept(FleetVisitor visitor) {
        visitor.visit(this);
    }
}
