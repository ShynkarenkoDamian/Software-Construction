package carsharing.task1_flyweight;

/**
 * Flyweight: спільні (внутрішні) дані моделі авто. Незмінний об'єкт,
 * один на модель, використовується всіма позначками цієї моделі.
 */
public class CarModel {

    private final String name;
    private final String icon;
    private final String characteristics;
    private final String photo;

    public CarModel(String name, String icon, String characteristics, String photo) {
        this.name = name;
        this.icon = icon;
        this.characteristics = characteristics;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public String getPhoto() {
        return photo;
    }
}
