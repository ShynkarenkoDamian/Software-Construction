package lab9.task1_objectpool;

/** Клієнт сервісу каршерингу. */
public class Client {

    private final String fullName;
    private final PersonalSettings settings;

    public Client(String fullName, PersonalSettings settings) {
        this.fullName = fullName;
        this.settings = settings;
    }

    public String getFullName() {
        return fullName;
    }

    public PersonalSettings getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
