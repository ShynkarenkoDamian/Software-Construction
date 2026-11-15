package carsharing.task3_bridge;

import carsharing.common.Log;

/** Канал доставки (ConcreteImplementor): у систему бухгалтерії клієнта. */
public class AccountingChannel implements ReportChannel {

    @Override
    public void deliver(String title, String body) {
        Log.log("AccountingChannel", "запис у систему бухгалтерії клієнта «" + title + "»: " + body);
    }
}
