package lab10.domain;

/** Клієнт сервісу каршерингу (ПІБ і посвідчення водія). */
public class Client {

    private final String fullName;
    private final String driverLicense;

    public Client(String fullName, String driverLicense) {
        this.fullName = fullName;
        this.driverLicense = driverLicense;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
