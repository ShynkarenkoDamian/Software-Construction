package carsharing.access;

/** ConcreteHandler: мінімальний стаж водіння (для преміальних авто). */
public class DrivingExperienceCheck extends AccessCheck {
    private final int minYears;

    public DrivingExperienceCheck(int minYears) {
        this.minYears = minYears;
    }

    @Override
    protected String verify(TripRequest request) {
        int years = request.getCustomer().getDrivingExperienceYears();
        if (years < minYears) {
            return "стаж водіння " + years + " р., потрібно щонайменше " + minYears + " р.";
        }
        return null;
    }
}
