package queues;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedQueue<Type> implements Iterable<Type> {
    private Type[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueue(int capacity) {
        data = (Type[]) new Object[capacity];
        size = 0;
    }

    public RandomizedQueue() {
        this(10);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Type item) {
        if (size >= data.length) resize(data.length * 2);
        data[size] = item;
        size++;
    }

    private int randomIndex() {
        return ThreadLocalRandom.current().nextInt(size); // random index within the number of items, regardless of the size of the array
    }

    public Type dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = randomIndex();
        Type item = data[index]; // copy of current item to return
        size--;
        data[index] = data[size]; // copy the last item into the current index
        if (size > 0 && size <= data.length / 4) resize(data.length / 2);
        else data[size] = null; // for garbage collection
        return item;
    }

    public Type sample() {
        return data[randomIndex()];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        iterator().forEachRemaining(sb::append); // add each item to the StringBuilder
        return sb.toString();
    }

    public Iterator<Type> iterator() {
        return new Iterator<>() {
            final RandomizedQueue<Integer> indexes;

            {
                indexes = new RandomizedQueue<>();
                for (int i = 0; i < size; i++) indexes.enqueue(i);
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

    private void resize(int capacity) {
//        @SuppressWarnings("unchecked")
//        Type[] newArray = (Type[]) new Object[capacity];
//        for (int i = 0; i < size; i++) newArray[i] = data[i];
//        data = newArray;
        data = Arrays.copyOf(data, capacity);
    }

    public static void main(String[] args) {
        RandomizedQueue<Character> queue = new RandomizedQueue<>();

        char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
        for (char c : chars) queue.enqueue(c);

        System.out.println("testing size: " + (queue.size() == chars.length));

        System.out.println("test iterator, should be randomized each time");
        for (int i = 0; i < 5; i++) {
            System.out.println(queue); // toString calls iterator
        }

        System.out.println("\ntest queries");
        for (int i = 0; i < chars.length; i++) {
            System.out.println(queue.dequeue());
            System.out.println(queue);
        }
    }
}