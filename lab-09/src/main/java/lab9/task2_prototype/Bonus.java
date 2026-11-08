package lab9.task2_prototype;

/** Бонус тарифу (бонусна програма). */
public class Bonus implements Prototype<Bonus> {

    private final String name;
    private final int percent;

    public Bonus(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public Bonus copy() {
        return new Bonus(name, percent);
    }

    @Override
    public String toString() {
        return name + " " + percent + "%";
    }
}
