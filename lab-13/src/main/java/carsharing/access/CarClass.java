package carsharing.access;

/** Клас авто: для кожного класу налаштовується свій ланцюг перевірок. */
public enum CarClass {
    STANDARD("звичайне авто"),
    PREMIUM("преміальне авто");

    private final String label;

    CarClass(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
