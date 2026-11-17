package carsharing.access;

/** ConcreteHandler: наявність потрібної суми на картці. */
public class FundsCheck extends AccessCheck {
    @Override
    protected String verify(TripRequest request) {
        int balance = request.getCustomer().getCardBalance();
        if (balance < request.getRequiredAmount()) {
            return "на картці " + balance + " грн, потрібно " + request.getRequiredAmount() + " грн";
        }
        return null;
    }
}
