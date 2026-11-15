package carsharing.task2_composite;

/**
 * Component: спільний інтерфейс для окремого майданчика і будь-якої зони.
 * Показники обчислюються однаково для будь-якого вузла дерева.
 */
public interface ZoneComponent {

    String getName();

    int getCarCount();

    /** Виручка в гривнях. */
    int getRevenue();

    /** Виводить вузол (і його нащадків) у журнал із відступом за рівнем вкладеності. */
    void print(int level);
}
