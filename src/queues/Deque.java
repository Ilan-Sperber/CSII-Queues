package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Type> implements Iterable<Type>{
    private class Node {
        final Type item;
        Node next;
        Node previous;
        Node(Type item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node first, last;
    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Type item) {
        Node oldFirst = first;
        first = new Node(item, oldFirst, null);
        if (isEmpty()) last = first; // if it was empty before this add there is just one element, so both first and last should be equal
        else oldFirst.previous = first; // oldFirst will be in front, so they should link. first.next is already linked
        size++;
    }

    public void addLast(Type item) {
        Node oldLast = last;
        last = new Node(item, null, oldLast);
        if (isEmpty()) first = last; // if it was empty before this add there is just one element, so both first and last should be equal
        else oldLast.next = last; // oldLast will be behind, so they should link. last.previous is already linked
        size++;
    }

    public Type removeFirst() {
        if (isEmpty()) throw new NoSuchElementException(); // could also return null?
        Type item = first.item; // copy
        first = first.next;
        size--;
        if (isEmpty()) last = null; // both will be null
        else first.previous = null; // unlink the old first to be garbage collected
        return item;
    }

    public Type removeLast() {
        if (isEmpty()) throw new NoSuchElementException(); // could also return null?
        Type item = last.item; // copy
        last = last.previous;
        size--;
        if (isEmpty()) first = null; // both will be null
        else last.next = null; // unlink the old last to be garbage collected
        return item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        iterator().forEachRemaining(sb::append); // add each item to the StringBuilder
        return sb.toString();
    }

    public Iterator<Type> iterator(){
        return new Iterator<>() {
            Node current = first;

            @Override
            public boolean hasNext() {
                return current != null; // will be null after last. This checks if the Node is null, the item doesn't matter
            }

            @Override
            public Type next() {
                if (!hasNext()) throw new NoSuchElementException(); // otherwise will throw a NullPointerException
                Type currentItem = current.item;
                current = current.next; // could be null, if so iterator is done
                return currentItem; // can't use current.previous.item since current could now be null
            }
        };
    }

    public static void main(String[] args) {
        Deque<Character> deque = new Deque<>();

        char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
//        char[] chars = {'a', 'b'};

        for (char c : chars) deque.addLast(c);

        // iterator tested in toString

        // show updating work
        System.out.println(deque);
        deque.removeFirst();
        System.out.println(deque);
        deque.removeLast();
        System.out.println(deque);
        deque.addFirst('F');
        System.out.println(deque);
        deque.addLast('L');
        System.out.println(deque);
        deque.removeFirst();
        System.out.println(deque);
        deque.removeLast();
        System.out.println(deque);
    }

}
