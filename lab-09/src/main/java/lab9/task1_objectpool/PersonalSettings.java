package lab9.task1_objectpool;

import java.util.ArrayList;
import java.util.List;

/** Персональні налаштування клієнта, які він переносить в авто під час оренди. */
public class PersonalSettings {

    private final String multimediaAccount;
    private final List<String> navigatorAddresses;
    private final int seatPosition;

    public PersonalSettings(String multimediaAccount, List<String> navigatorAddresses, int seatPosition) {
        this.multimediaAccount = multimediaAccount;
        this.navigatorAddresses = new ArrayList<>(navigatorAddresses);
        this.seatPosition = seatPosition;
    }

    public String getMultimediaAccount() {
        return multimediaAccount;
    }

    public List<String> getNavigatorAddresses() {
        return navigatorAddresses;
    }

    public int getSeatPosition() {
        return seatPosition;
    }
}
