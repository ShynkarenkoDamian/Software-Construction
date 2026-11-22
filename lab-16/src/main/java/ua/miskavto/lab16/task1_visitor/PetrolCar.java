package ua.miskavto.lab16.task1_visitor;

/** ConcreteElement: бензинове. */
public class PetrolCar extends Vehicle {

    public PetrolCar(String plate, double value, int mileageKm, int monthlyKm) {
        super(plate, value, mileageKm, monthlyKm);
    }

    @Override
    public String typeName() {
        return "бензинове";
    }

    @Override
    public void accept(FleetVisitor visitor) {
        visitor.visit(this);
    }
}
