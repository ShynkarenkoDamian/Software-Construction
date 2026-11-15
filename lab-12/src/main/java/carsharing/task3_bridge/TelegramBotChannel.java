package carsharing.task3_bridge;

import carsharing.common.Log;

/** Новий канал доставки (ConcreteImplementor): Telegram-бот. Додається одним класом, решта коду не змінюється. */
public class TelegramBotChannel implements ReportChannel {

    @Override
    public void deliver(String title, String body) {
        Log.log("TelegramBotChannel", "повідомлення Telegram-бота «" + title + "»: " + body);
    }
}
