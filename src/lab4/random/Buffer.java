package lab4.random;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    final int maxSize;
    private int ptrInsert;
    private int ptrGet;
    private int size;
    private Integer[] buffer;

    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

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

    public void produce(int element, int howMany){
        lock.lock();
        try {
            while (!isEnoughPlace(howMany)) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < howMany; i++) {
                this.buffer[ptrInsert] = element;
                this.ptrInsert = (this.ptrInsert + 1) % this.maxSize;
                this.size += 1;
                System.out.printf(i + "Produced element:" + element + "\n");
            }
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int howMany){
        int element;
        lock.lock();
        try {
            while (!this.isEnoughElements(howMany)) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < howMany; i++) {
                element = this.buffer[ptrGet];
                this.buffer[ptrGet] = 0;
                this.ptrGet = (this.ptrGet + 1) % this.maxSize;
                this.size -= 1;
                System.out.printf(i + "Consumed element:" + element + "\n");
            }
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
