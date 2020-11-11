package lab3;

import java.util.ArrayList;

public class ProducersConsumers {
    private static int producersNumber = 100;
    private static int consumersNumber = 100;
    private static int maxSize = 3;

    public static void main(String[] args){
        Buffer buffer = new Buffer(maxSize);
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for(int i = 0; i < producersNumber; i++){
            producers.add(new Thread(new Producer(buffer)));
            producers.get(i).start();
        }
        for(int i = 0; i < consumersNumber; i++){
            consumers.add(new Thread(new Consumer(buffer)));
            consumers.get(i).start();
        }
    }

}
