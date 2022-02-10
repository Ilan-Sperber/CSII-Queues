package client;

import queues.RandomizedQueue;

import java.util.Scanner;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many entries should we give back? ");
        int strings = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter words or phrases or whatever i dont rlly care");

        String in;
        while (!(in = scanner.nextLine()).isBlank()) queue.enqueue(in);

        System.out.println("\nEnjoy your sentence\n");
        for (int i = 0; i < strings; i++) {
            if (i > 0) System.out.print(' ');
            System.out.print(queue.dequeue());
        }
    }
}
