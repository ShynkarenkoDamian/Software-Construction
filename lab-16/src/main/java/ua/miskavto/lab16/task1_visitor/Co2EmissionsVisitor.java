package ua.miskavto.lab16.task1_visitor;

import ua.miskavto.lab16.common.Log;

import java.util.Locale;

/**
 * ConcreteVisitor: викиди CO2 для звіту міській раді.
 * Додано ОДНИМ класом: Vehicle, Fleet та інші візитори не змінювалися.
 */
public class Co2EmissionsVisitor implements FleetVisitor {

    private static final String SOURCE = "Co2EmissionsVisitor";
    private double totalKg;

    @Override
    public void visit(PetrolCar car) {
        add(car, 150);
    }

    @Override
    public void visit(HybridCar car) {
        add(car, 90);
    }

    @Override
    public void visit(ElectricCar car) {
        add(car, 0);
    }

    @Override
    public void visit(Minibus minibus) {
        add(minibus, 230);
    }

    private void add(Vehicle vehicle, int gramsPerKm) {
        double kg = vehicle.getMonthlyKm() * gramsPerKm / 1000.0;
        totalKg += kg;
        Log.log(SOURCE, vehicle.getPlate() + " (" + vehicle.typeName() + "): " + gramsPerKm + " г/км x "
                + vehicle.getMonthlyKm() + " км = " + String.format(Locale.ROOT, "%.1f", kg) + " кг CO2");
    }

    @Override
    public void onFinish() {
        Log.log(SOURCE, "Разом за місяць: " + String.format(Locale.ROOT, "%.1f", totalKg) + " кг CO2");
    }
}
