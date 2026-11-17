package carsharing.model;

/** Клієнт каршерингу: дані, за якими перевіряється допуск до поїздки. */
public class Customer {
    private final String name;
    private final boolean licenseVerified;
    private final int drivingExperienceYears;
    private final int debt;          // заборгованість, грн
    private final int cardBalance;   // кошти на картці, грн
    private final boolean banned;    // заблокований за порушення

    public Customer(String name, boolean licenseVerified, int drivingExperienceYears,
                    int debt, int cardBalance, boolean banned) {
        this.name = name;
        this.licenseVerified = licenseVerified;
        this.drivingExperienceYears = drivingExperienceYears;
        this.debt = debt;
        this.cardBalance = cardBalance;
        this.banned = banned;
    }

    public String getName() {
        return name;
    }

    public boolean isLicenseVerified() {
        return licenseVerified;
    }

    public int getDrivingExperienceYears() {
        return drivingExperienceYears;
    }

    public int getDebt() {
        return debt;
    }

    public int getCardBalance() {
        return cardBalance;
    }

    public boolean isBanned() {
        return banned;
    }
}
