package carsharing.access;

/** ConcreteHandler: підтвердження посвідчення водія. */
public class DriverLicenseCheck extends AccessCheck {
    @Override
    protected String verify(TripRequest request) {
        if (!request.getCustomer().isLicenseVerified()) {
            return "посвідчення водія не підтверджено";
        }
        return null;
    }
}
