package ua.miskavto.lab16.task1_visitor;

import ua.miskavto.lab16.common.Log;

/** ConcreteVisitor: розрахунок вартості страхування парку (річна премія = відсоток від вартості авто). */
public class InsuranceVisitor implements FleetVisitor {

    private static final String SOURCE = "InsuranceVisitor";
    private double total;

    @Override
    public void visit(PetrolCar car) {
        add(car, car.getValue() * 0.04, "4% від вартості");
    }

    @Override
    public void visit(HybridCar car) {
        add(car, car.getValue() * 0.045, "4.5% від вартості");
    }

    @Override
    public void visit(ElectricCar car) {
        add(car, car.getValue() * 0.05, "5% від вартості (дорогий ремонт батареї)");
    }

    @Override
    public void visit(Minibus minibus) {
        add(minibus, minibus.getValue() * 0.06 + minibus.getSeats() * 400,
                "6% від вартості + 400 грн за кожне з " + minibus.getSeats() + " місць");
    }

    private void add(Vehicle vehicle, double premium, String rule) {
        total += premium;
        Log.log(SOURCE, vehicle.getPlate() + " (" + vehicle.typeName() + "): страхування " + Log.money(premium)
                + " — " + rule);
    }

    @Override
    public void onFinish() {
        Log.log(SOURCE, "Разом за парк: " + Log.money(total));
    }
}
