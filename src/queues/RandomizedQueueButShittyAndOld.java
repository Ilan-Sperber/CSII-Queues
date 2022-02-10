package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedQueueButShittyAndOld<Type> implements Iterable<Type> {
    private Type[] data;
    private Stack<Integer> available;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueueButShittyAndOld(int capacity) {
        size = 0;
        data = (Type[]) new Object[capacity];
        available = new Stack<>();
        for (int i = 0; i < capacity; i++) available.push(i);
    }

    public RandomizedQueueButShittyAndOld() {
        this(10);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Type item) {
        data[available.pop()] = item;
        size++;
        // TODO: resize
    }

    public Type dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = ThreadLocalRandom.current().nextInt(data.length); // random index in the array
        Type item = data[index]; // copy
        data[index] = null; // remove the storage from the array
        size--;
        available.push(index); // this index is now available for something else
        return item;
    }

    public Type sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return data[ThreadLocalRandom.current().nextInt(data.length)];
    }

    public Iterator<Type> iterator() {
        return new Iterator<>() {
            final RandomizedQueueButShittyAndOld<Integer> indexes;
            {
                indexes = new RandomizedQueueButShittyAndOld<>(data.length);
                for (int i = 0; i < data.length; i++) indexes.enqueue(i);
            }

            @Override
            public boolean hasNext() {
                return !indexes.isEmpty();
            }

            @Override
            public Type next() {
                return data[indexes.dequeue()];
            }
        };
    }

    public static void main(String[] args) {

    }
}
