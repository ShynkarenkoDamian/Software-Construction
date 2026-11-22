package ua.miskavto.lab16.task1_visitor;

/** ConcreteElement: мікроавтобус. */
public class Minibus extends Vehicle {

    private final int seats;

    public Minibus(String plate, double value, int mileageKm, int monthlyKm, int seats) {
        super(plate, value, mileageKm, monthlyKm);
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String typeName() {
        return "мікроавтобус";
    }

    @Override
    public void accept(FleetVisitor visitor) {
        visitor.visit(this);
    }
}
