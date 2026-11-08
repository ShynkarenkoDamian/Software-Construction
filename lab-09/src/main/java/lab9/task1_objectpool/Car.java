package lab9.task1_objectpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Автомобіль парку — повторно використовуваний об'єкт пулу (Reusable).
 * <p>
 * Конструктор пакетний: створити авто в обхід {@link CarPool} неможливо.
 * Персональні дані попереднього клієнта стираються методом {@link #resetForNextRental()}.
 */
public class Car {

    public static final int DEFAULT_SEAT_POSITION = 3;

    private final String plateNumber;
    private final String model;
    private CarState state = CarState.FREE;
    private Client currentClient;

    // персональні дані поточного клієнта — те, що треба стирати між орендами
    private String multimediaAccount;
    private final List<String> navigatorAddresses = new ArrayList<>();
    private int seatPosition = DEFAULT_SEAT_POSITION;

    private int tripsCount;

    Car(String plateNumber, String model) {
        this.plateNumber = plateNumber;
        this.model = model;
    }

    /** Видача авто клієнту (викликається лише пулом). */
    void handOutTo(Client client) {
        this.state = CarState.RENTED;
        this.currentClient = client;
        this.tripsCount++;
    }

    /** Скидання стану перед повторною видачею (викликається лише пулом). */
    void resetForNextRental() {
        this.multimediaAccount = null;
        this.navigatorAddresses.clear();
        this.seatPosition = DEFAULT_SEAT_POSITION;
        this.currentClient = null;
        this.state = CarState.FREE;
    }

    /** Клієнт заходить у свій акаунт мультимедіа, зберігає адреси, налаштовує сидіння. */
    public void signIn(PersonalSettings settings) {
        if (state != CarState.RENTED) {
            throw new IllegalStateException("Авто " + plateNumber + " не в оренді");
        }
        this.multimediaAccount = settings.getMultimediaAccount();
        this.navigatorAddresses.clear();
        this.navigatorAddresses.addAll(settings.getNavigatorAddresses());
        this.seatPosition = settings.getSeatPosition();
    }

    /** Короткий опис персональних даних для журналу. */
    public String describePersonalData() {
        String account = multimediaAccount == null ? "відсутній" : multimediaAccount;
        String addresses = navigatorAddresses.isEmpty() ? "немає" : String.join("; ", navigatorAddresses);
        String seat = seatPosition == DEFAULT_SEAT_POSITION ? "типове (" + seatPosition + ")" : String.valueOf(seatPosition);
        return "акаунт мультимедіа: " + account + "; адреси навігатора: " + addresses + "; сидіння: " + seat;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModel() {
        return model;
    }

    public CarState getState() {
        return state;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public int getTripsCount() {
        return tripsCount;
    }

    public List<String> getNavigatorAddresses() {
        return Collections.unmodifiableList(navigatorAddresses);
    }

    public String getMultimediaAccount() {
        return multimediaAccount;
    }

    @Override
    public String toString() {
        return plateNumber;
    }
}
