package carsharing.access;

/** ConcreteHandler: відсутність заборгованості. */
public class DebtCheck extends AccessCheck {
    @Override
    protected String verify(TripRequest request) {
        int debt = request.getCustomer().getDebt();
        if (debt > 0) {
            return "є заборгованість " + debt + " грн";
        }
        return null;
    }
}
