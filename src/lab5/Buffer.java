package lab5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    final int maxSize;
    private int ptrInsert;
    private int ptrGet;
    private int size;
    private Integer[] buffer;

    final ReentrantLock lock = new ReentrantLock();
    final Condition restProducers = lock.newCondition();
    final Condition restConsumers = lock.newCondition();
    final Condition firstConsumer = lock.newCondition();
    final Condition firstProducer = lock.newCondition();

    int restProducersCount = 0;
    int restConsumersCount = 0;
    int firstConsumerCount = 0;
    int firstProducerCount = 0;

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.ptrInsert = 0;
        this.ptrGet = 0;
        this.size = 0;
        this.buffer = new Integer[maxSize];
        for(int i = 0; i<maxSize; i++) buffer[i]=0;
    }

    private boolean isEnoughPlace(int howMany){
        return this.size + howMany <= this.maxSize;
    }

    private boolean isEnoughElements(int howMany){
        return this.size - howMany >= 0;
    }

    private void printState(){
        System.out.printf("________________________________________\n" +
                          "Waiting producers: " + this.firstProducerCount + " " + this.restProducersCount + "\n" +
                          "Waiting consumers: " + this.firstConsumerCount + " " + this.restConsumersCount + "\n" +
                          "________________________________________\n");
    }

    public void produce(int element, int howMany){
        lock.lock();
        try {
            while (lock.hasWaiters(firstProducer)){
                try {
                    restProducersCount += 1;
                    this.printState();
                    restProducers.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!isEnoughPlace(howMany)) {
                try {
                    firstProducerCount += 1;
                    this.printState();
                    firstProducer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < howMany; i++) {
                this.buffer[ptrInsert] = element;
                this.ptrInsert = (this.ptrInsert + 1) % this.maxSize;
                this.size += 1;
            }
            System.out.printf("Produced " + howMany + " elements. Buffer state:" + this.size + "/" + this.maxSize + "\n");
            restProducers.signal();
            if (restProducersCount > 0) {
                restProducersCount -= 1;
                this.printState();
            }
            firstConsumer.signal();
            if (firstConsumerCount > 0) {
                firstConsumerCount -= 1;
                this.printState();
            }
        } finally {
            lock.unlock();
        }
    }

    public void consume(int howMany){
        lock.lock();
        try {
            while(lock.hasWaiters(firstConsumer)){
                try {
                    restConsumersCount += 1;
                    this.printState();
                    restConsumers.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!this.isEnoughElements(howMany)) {
                try {
                    firstConsumerCount += 1;
                    this.printState();
                    firstConsumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < howMany; i++) {
                this.buffer[ptrGet] = 0;
                this.ptrGet = (this.ptrGet + 1) % this.maxSize;
                this.size -= 1;
            }
            System.out.printf("Consumed " + howMany + " elements. Buffer state:" + this.size + "/" + this.maxSize + "\n");
            restConsumers.signal();
            if (restConsumersCount > 0) {
                restConsumersCount -= 1;
                this.printState();
            }
            firstProducer.signal();
            if (firstProducerCount > 0) {
                firstProducerCount -= 1;
                this.printState();
            }
        } finally {
            lock.unlock();
        }
    }
}
