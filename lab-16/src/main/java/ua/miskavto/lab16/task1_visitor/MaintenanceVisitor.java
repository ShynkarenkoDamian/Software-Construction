package ua.miskavto.lab16.task1_visitor;

import ua.miskavto.lab16.common.Log;

/** ConcreteVisitor: планування технічного обслуговування (за пробігом). */
public class MaintenanceVisitor implements FleetVisitor {

    private static final String SOURCE = "MaintenanceVisitor";
    private int planned;

    @Override
    public void visit(PetrolCar car) {
        plan(car, 10_000, "заміна оливи й фільтрів, перевірка гальм");
    }

    @Override
    public void visit(HybridCar car) {
        plan(car, 15_000, "оливи й фільтрів ДВЗ, діагностика тягового акумулятора");
    }

    @Override
    public void visit(ElectricCar car) {
        plan(car, 20_000, "діагностика батареї, гальма, салонний фільтр");
    }

    @Override
    public void visit(Minibus minibus) {
        plan(minibus, 8_000, "ходова, гальма, заміна оливи й фільтрів");
    }

    private void plan(Vehicle vehicle, int intervalKm, String works) {
        int nextKm = (vehicle.getMileageKm() / intervalKm + 1) * intervalKm;
        int left = nextKm - vehicle.getMileageKm();
        planned++;
        Log.log(SOURCE, vehicle.getPlate() + " (" + vehicle.typeName() + "): ТО кожні " + intervalKm
                + " км, наступне на " + nextKm + " км (через " + left + " км): " + works);
    }

    @Override
    public void onFinish() {
        Log.log(SOURCE, "Заплановано ТО для " + planned + " авто");
    }
}
