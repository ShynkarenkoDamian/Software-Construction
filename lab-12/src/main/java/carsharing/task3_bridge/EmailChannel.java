package carsharing.task3_bridge;

import carsharing.common.Log;

/** Канал доставки (ConcreteImplementor): на e-mail. */
public class EmailChannel implements ReportChannel {

    @Override
    public void deliver(String title, String body) {
        Log.log("EmailChannel", "лист на e-mail, тема «" + title + "»: " + body);
    }
}
