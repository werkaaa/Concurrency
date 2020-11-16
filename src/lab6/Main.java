package lab6;

import java.util.ArrayList;

public class Main {
    private static int producersNumber = 100;
    private static int consumersNumber = 100;
    private static int maxSize = 1000000;

    public static void main(String[] args){
        Buffer buffer = new Buffer(maxSize);
        Monitor monitor = new Monitor(maxSize);
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for(int i = 0; i < consumersNumber; i++){
            consumers.add(new Thread(new Consumer(monitor, buffer)));
            consumers.get(i).start();
        }

        for(int i = 0; i < producersNumber; i++){
            producers.add(new Thread(new Producer(monitor, buffer)));
            producers.get(i).start();
        }
    }

}