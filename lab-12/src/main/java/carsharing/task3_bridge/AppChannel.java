package carsharing.task3_bridge;

import carsharing.common.Log;

/** Канал доставки (ConcreteImplementor): у мобільний застосунок. */
public class AppChannel implements ReportChannel {

    @Override
    public void deliver(String title, String body) {
        Log.log("AppChannel", "push-повідомлення в застосунок «" + title + "»: " + body);
    }
}
