package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Type> implements Iterable<Type> {
    private class Node {
        final Type item;
        final Node next;
        Node(Type item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public Type pop() {
        if (isEmpty()) throw new NoSuchElementException();
        Type popped = first.item;
        first = first.next;
        return popped;
    }

    public void push(Type item) {
        first = new Node(item, first);
    }

    @Override
    public Iterator<Type> iterator() {
        return new Iterator<>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Type next() {
                if (!hasNext()) throw new NoSuchElementException();
                Type copy = current.item;
                current = current.next;
                return copy;
            }
        };
    }
}
