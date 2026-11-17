package carsharing.access;

/** Підсумок проходження ланцюга: допущено чи ні, яка ланка поставила крапку і чому. */
public record AccessResult(boolean granted, String stoppedBy, String reason) {

    public static AccessResult granted(String lastCheck) {
        return new AccessResult(true, lastCheck, null);
    }

    public static AccessResult denied(String check, String reason) {
        return new AccessResult(false, check, reason);
    }
}
