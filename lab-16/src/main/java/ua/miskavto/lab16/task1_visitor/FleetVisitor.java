package ua.miskavto.lab16.task1_visitor;

/** Visitor: окремий метод visit() для кожного типу авто. Нова операція над парком = новий клас, що реалізує цей інтерфейс. */
public interface FleetVisitor {

    void visit(PetrolCar car);

    void visit(HybridCar car);

    void visit(ElectricCar car);

    void visit(Minibus minibus);

    /** Викликається після обходу всього парку (підсумок операції). */
    default void onFinish() {
    }
}
