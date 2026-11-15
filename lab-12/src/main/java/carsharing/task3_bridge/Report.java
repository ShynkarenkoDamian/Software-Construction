package carsharing.task3_bridge;

import carsharing.common.Log;

/**
 * Вид звіту (Abstraction): відповідає на питання «що містить звіт».
 * Містить посилання на канал (це і є міст) і делегує йому доставку.
 */
public abstract class Report {

    private final ReportChannel channel;

    protected Report(ReportChannel channel) {
        this.channel = channel;
    }

    public final void send() {
        Log.log(getClass().getSimpleName(), "формую звіт «" + getTitle() + "»");
        channel.deliver(getTitle(), buildBody());
    }

    protected abstract String getTitle();

    protected abstract String buildBody();
}
