package lab5;

import java.util.ArrayList;

public class ProducersConsumers {
    private static int producersNumber = 1;
    private static int consumersNumber = 2;
    private static int maxSize = 10;
    private static int maxProduced = 5;

    public static void main(String[] args){
        Buffer buffer = new Buffer(maxSize);
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for(int i = 0; i < producersNumber; i++){
            producers.add(new Thread(new Producer(buffer, maxProduced)));
            producers.get(i).start();
        }
        for(int i = 0; i < consumersNumber; i++){
            consumers.add(new Thread(new Consumer(buffer, maxProduced)));
            consumers.get(i).start();
        }
    }

}
