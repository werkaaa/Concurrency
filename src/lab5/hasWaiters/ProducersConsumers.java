package lab5.hasWaiters;

import java.util.ArrayList;

public class ProducersConsumers {
    private static int producersNumber = 1;
    private static int consumersNumber = 2;
    private static int maxSize = 100; // For easy deadlock: 10 | For easy starvation: 100
    private static int maxElements = 5; // For easy deadlock and starvation: 5

    public static void main(String[] args){
        Buffer buffer = new Buffer(maxSize);
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for(int i = 0; i < producersNumber; i++){
            producers.add(new Thread(new Producer(i, buffer, maxElements)));
            producers.get(i).start();
        }
        for(int i = 0; i < consumersNumber; i++){
            consumers.add(new Thread(new Consumer(i, buffer, maxElements)));
            consumers.get(i).start();
        }

        // Force thread starvation
        new Thread(new Consumer(consumersNumber, buffer, 50)).start();
    }

}
