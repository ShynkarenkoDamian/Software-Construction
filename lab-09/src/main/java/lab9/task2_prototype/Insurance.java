package lab9.task2_prototype;

/** Страхування, що входить до тарифу (вкладений об'єкт, змінюваний). */
public class Insurance implements Prototype<Insurance> {

    private InsuranceType type;

    public Insurance(InsuranceType type) {
        this.type = type;
    }

    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
    }

    @Override
    public Insurance copy() {
        return new Insurance(type);
    }
}
