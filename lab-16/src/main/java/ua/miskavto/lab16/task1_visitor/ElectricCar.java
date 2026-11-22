package ua.miskavto.lab16.task1_visitor;

/** ConcreteElement: електромобіль. */
public class ElectricCar extends Vehicle {

    public ElectricCar(String plate, double value, int mileageKm, int monthlyKm) {
        super(plate, value, mileageKm, monthlyKm);
    }

    @Override
    public String typeName() {
        return "електромобіль";
    }

    @Override
    public void accept(FleetVisitor visitor) {
        visitor.visit(this);
    }
}
