package carsharing.task1_flyweight;

import carsharing.common.Log;
import java.util.HashMap;
import java.util.Map;

/**
 * FlyweightFactory: зберігає створені моделі й повертає наявну,
 * а нову створює лише тоді, коли такої моделі ще не було.
 */
public class CarModelFactory {

    private final Map<String, CarModel> models = new HashMap<>();

    public CarModel getModel(String name, String icon, String characteristics, String photo) {
        CarModel model = models.get(name);
        if (model == null) {
            model = new CarModel(name, icon, characteristics, photo);
            models.put(name, model);
            Log.log("CarModelFactory", "створено новий об'єкт моделі " + name
                    + " (усього об'єктів моделей: " + models.size() + ")");
        }
        return model;
    }

    public int getModelCount() {
        return models.size();
    }
}
