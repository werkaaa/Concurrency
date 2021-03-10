package lab7;

import java.util.ArrayList;


public class Main {
    private static final int producersNumber = 3;
    private static final int consumersNumber = 20;
    private static final int maxSize = 100;
    private static final int maxElements = 50;

    public static void main(String[] args) {
        ProxyBuffer proxyBuffer = new ProxyBuffer(maxSize);
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < consumersNumber; i++) {
            consumers.add(new Thread(new Consumer(i, proxyBuffer, maxElements)));
            consumers.get(i).start();
        }

        for (int i = 0; i < producersNumber; i++) {
            producers.add(new Thread(new Producer(i, proxyBuffer, maxElements)));
            producers.get(i).start();
        }
    }

}
