package lab9.task2_prototype;

/** Зона, у якій дозволено завершити поїздку. */
public class EndZone implements Prototype<EndZone> {

    private final String name;

    public EndZone(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public EndZone copy() {
        return new EndZone(name);
    }
}
