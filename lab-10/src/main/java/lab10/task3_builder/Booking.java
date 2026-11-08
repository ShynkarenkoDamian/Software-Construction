package lab10.task3_builder;

import lab10.common.ConsoleLogger;
import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Бронювання авто — продукт шаблону Builder.
 * <p>
 * Клас незмінний: усі поля {@code final}, сеттерів немає. Конструктор приватний, тому створити
 * бронювання можна лише через {@link Builder#build()}, а отже — лише після перевірки обов'язкових частин.
 */
public final class Booking {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final Client client;
    private final Car car;
    private final LocalDateTime startTime;
    private final boolean childSeat;
    private final boolean extraInsurance;
    private final String deliveryAddress;
    private final String comment;

    private Booking(Builder builder) {
        this.client = builder.client;
        this.car = builder.car;
        this.startTime = builder.startTime;
        this.childSeat = builder.childSeat;
        this.extraInsurance = builder.extraInsurance;
        this.deliveryAddress = builder.deliveryAddress;
        this.comment = builder.comment;
    }

    public Client getClient() {
        return client;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean hasChildSeat() {
        return childSeat;
    }

    public boolean hasExtraInsurance() {
        return extraInsurance;
    }

    /** Адреса подачі авто; {@code null}, якщо подача не замовлена. */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /** Коментар клієнта; {@code null}, якщо його немає. */
    public String getComment() {
        return comment;
    }

    /** Читабельний опис бронювання: з яких частин воно складається. */
    public String describe() {
        return "Бронювання:\n"
                + "  Клієнт: " + client + " (посвідчення " + client.getDriverLicense() + ")\n"
                + "  Авто: " + car + "\n"
                + "  Початок: " + startTime.format(TIME_FORMAT) + "\n"
                + "  Дитяче крісло: " + yesNo(childSeat) + "\n"
                + "  Додаткове страхування: " + yesNo(extraInsurance) + "\n"
                + "  Подача до адреси: " + (deliveryAddress == null ? "ні" : deliveryAddress) + "\n"
                + "  Коментар: " + (comment == null ? "—" : comment);
    }

    @Override
    public String toString() {
        return describe();
    }

    private static String yesNo(boolean value) {
        return value ? "так" : "ні";
    }

    /**
     * Будівельник бронювання: кожна частина задається окремим кроком {@code withXxx(...)},
     * а обов'язкові частини (клієнт, авто, час початку не в минулому) перевіряються в {@link #build()}.
     */
    public static final class Builder {

        private static final String SOURCE = "BookingBuilder";

        private final Clock clock;
        private Client client;
        private Car car;
        private LocalDateTime startTime;
        private boolean childSeat;
        private boolean extraInsurance;
        private String deliveryAddress;
        private String comment;

        /** Годинник потрібен для перевірки «час початку не в минулому» (у демонстрації він фіксований). */
        public Builder(Clock clock) {
            this.clock = clock;
        }

        public Builder withClient(Client client) {
            this.client = client;
            ConsoleLogger.log(SOURCE, "Крок: клієнт — " + client);
            return this;
        }

        public Builder withCar(Car car) {
            this.car = car;
            ConsoleLogger.log(SOURCE, "Крок: авто — " + car);
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            ConsoleLogger.log(SOURCE, "Крок: час початку — "
                    + (startTime == null ? "не задано" : startTime.format(TIME_FORMAT)));
            return this;
        }

        public Builder withChildSeat() {
            this.childSeat = true;
            ConsoleLogger.log(SOURCE, "Крок: дитяче крісло");
            return this;
        }

        public Builder withExtraInsurance() {
            this.extraInsurance = true;
            ConsoleLogger.log(SOURCE, "Крок: додаткове страхування");
            return this;
        }

        public Builder withDeliveryTo(String address) {
            this.deliveryAddress = address;
            ConsoleLogger.log(SOURCE, "Крок: подача до адреси — " + address);
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            ConsoleLogger.log(SOURCE, "Крок: коментар — " + comment);
            return this;
        }

        /**
         * Завершальний крок: перевіряє обов'язкові частини і створює незмінне бронювання.
         *
         * @throws IllegalStateException якщо бронювання неповне або некоректне
         */
        public Booking build() {
            if (client == null) {
                fail("не вказано клієнта");
            }
            if (car == null) {
                fail("не обрано автомобіль");
            }
            if (startTime == null) {
                fail("не вказано час початку");
            }
            if (startTime.isBefore(LocalDateTime.now(clock))) {
                fail("час початку в минулому");
            }
            Booking booking = new Booking(this);
            ConsoleLogger.log(SOURCE, "Бронювання сформовано: клієнт " + client + ", авто " + car
                    + ", початок " + startTime.format(TIME_FORMAT));
            return booking;
        }

        private void fail(String reason) {
            String message = "Неможливо сформувати бронювання: " + reason;
            ConsoleLogger.log(SOURCE, message);
            throw new IllegalStateException(message);
        }
    }
}
