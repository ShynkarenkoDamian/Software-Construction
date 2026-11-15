package carsharing.task1_flyweight;

import java.util.ArrayList;
import java.util.List;

/** Мапа застосунку (Client): зберігає позначки. */
public class RouteMap {

    private final List<CarMarker> markers = new ArrayList<>();

    public void addMarker(CarMarker marker) {
        markers.add(marker);
    }

    public CarMarker getMarker(int index) {
        return markers.get(index);
    }

    public int getMarkerCount() {
        return markers.size();
    }
}
