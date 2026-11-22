package ua.miskavto.lab16.task3_iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Базовий ітератор з фільтром: пропускає авто, які не підходять під умову accepts(). */
public abstract class FilteredCarIterator implements Iterator<ParkedCar> {

    private final Iterator<ParkedCar> source;
    private ParkedCar nextCar;

    protected FilteredCarIterator(Iterator<ParkedCar> source) {
        this.source = source;
    }

    protected abstract boolean accepts(ParkedCar car);

    @Override
    public boolean hasNext() {
        while (nextCar == null && source.hasNext()) {
            ParkedCar candidate = source.next();
            if (accepts(candidate)) {
                nextCar = candidate;
            }
        }
        return nextCar != null;
    }

    @Override
    public ParkedCar next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ParkedCar result = nextCar;
        nextCar = null;
        return result;
    }
}
