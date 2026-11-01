package logistics.domain;

/**
 * Стабільний контракт для змінної частини: "як тип доставки впливає на ціну".
 * Protected Variations: ядро системи залежить лише від цього інтерфейсу.
 * Polymorphism: кожен тип доставки — окрема реалізація, if-else більше не потрібні.
 */
public interface DeliveryTariff {

    /** Назва типу доставки (Standard / Express / Freight). */
    String name();

    /** Застосовує коефіцієнт тарифу до базової вартості перевезення. */
    double applyTo(double baseFee);
}
