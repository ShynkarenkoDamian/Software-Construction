package carsharing.task2_composite;

import carsharing.common.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite: зона (місто, район, квартал, бізнес-центр...). Містить будь-які
 * ZoneComponent, тобто майданчики або інші зони, і сама рахує показники по нащадках.
 */
public class Zone implements ZoneComponent {

    private final String name;
    private final List<ZoneComponent> children = new ArrayList<>();

    public Zone(String name) {
        this.name = name;
    }

    public void add(ZoneComponent child) {
        children.add(child);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCarCount() {
        int sum = 0;
        for (ZoneComponent child : children) {
            sum += child.getCarCount();
        }
        return sum;
    }

    @Override
    public int getRevenue() {
        int sum = 0;
        for (ZoneComponent child : children) {
            sum += child.getRevenue();
        }
        return sum;
    }

    @Override
    public void print(int level) {
        Log.log("Zone", "  ".repeat(level) + "+ " + name + ": авто " + getCarCount() + ", виручка " + getRevenue() + " грн");
        for (ZoneComponent child : children) {
            child.print(level + 1);
        }
    }
}
