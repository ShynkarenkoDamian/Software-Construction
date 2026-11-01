package logistics.application;

/**
 * Pure Fabrication: технічний генератор номерів (у legacy-коді номери були "зашиті" в код).
 */
public class IdSequence {

    private int next;

    public IdSequence(int start) {
        this.next = start;
    }

    public int next() {
        return next++;
    }
}
