package carsharing.access;

/** ConcreteHandler: відсутність блокування за порушення. */
public class BanCheck extends AccessCheck {
    @Override
    protected String verify(TripRequest request) {
        if (request.getCustomer().isBanned()) {
            return "клієнта заблоковано за порушення";
        }
        return null;
    }
}
