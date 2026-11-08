package lab9.task2_prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Тарифний план поїздки — конкретний прототип.
 * <p>
 * Містить вкладені змінювані об'єкти (страхування, зони завершення, бонуси), тому
 * {@link #copy()} виконує глибоке копіювання: для кожного вкладеного об'єкта
 * створюється новий екземпляр, а списки не розділяються з оригіналом.
 */
public class TariffPlan implements Prototype<TariffPlan> {

    private static final Locale UA = Locale.forLanguageTag("uk-UA");

    private String name;
    private double pricePerMinute;
    private double pricePerKm;
    private Insurance insurance;
    private int speedLimit;
    private final List<EndZone> endZones = new ArrayList<>();
    private final List<Bonus> bonuses = new ArrayList<>();

    public TariffPlan(String name, double pricePerMinute, double pricePerKm,
                      Insurance insurance, int speedLimit) {
        this.name = name;
        this.pricePerMinute = pricePerMinute;
        this.pricePerKm = pricePerKm;
        this.insurance = insurance;
        this.speedLimit = speedLimit;
    }

    /** Конструктор копіювання — лише для {@link #copy()}. */
    private TariffPlan(TariffPlan source) {
        this.name = source.name;
        this.pricePerMinute = source.pricePerMinute;
        this.pricePerKm = source.pricePerKm;
        this.speedLimit = source.speedLimit;
        this.insurance = source.insurance.copy();
        for (EndZone zone : source.endZones) {
            this.endZones.add(zone.copy());
        }
        for (Bonus bonus : source.bonuses) {
            this.bonuses.add(bonus.copy());
        }
    }

    @Override
    public TariffPlan copy() {
        return new TariffPlan(this);
    }

    public void addEndZone(EndZone zone) {
        endZones.add(zone);
    }

    public void addBonus(Bonus bonus) {
        bonuses.add(bonus);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public List<EndZone> getEndZones() {
        return Collections.unmodifiableList(endZones);
    }

    public List<Bonus> getBonuses() {
        return Collections.unmodifiableList(bonuses);
    }

    /** Опис тарифу рядками — для виведення «поруч» і для перевірки незмінності. */
    public List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("Тариф: " + name);
        lines.add("Ціна за хвилину: " + String.format(UA, "%.2f", pricePerMinute) + " грн");
        lines.add("Ціна за км: " + String.format(UA, "%.2f", pricePerKm) + " грн");
        lines.add("Страхування: " + insurance.getType());
        lines.add("Обмеження швидкості: " + speedLimit + " км/год");
        lines.add("Зони завершення: " + endZones.stream().map(EndZone::getName).collect(Collectors.joining(", ")));
        lines.add("Бонуси: " + bonuses.stream().map(Bonus::toString).collect(Collectors.joining(", ")));
        return lines;
    }
}
