package homework5;

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

    private boolean isFull(){
        return this.size >= this.maxSize;
    }

    private boolean isEmpty(){
        return this.size <= 0;
    }

    public void produce(int element){
        lock.lock();
        try {
            while (this.isFull()) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.buffer[ptrInsert] = element;
            this.ptrInsert = (this.ptrInsert + 1) % this.maxSize;
            this.size += 1;
            System.out.printf("Produced element:" + element + "\n");

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int consume(){
        int element;
        lock.lock();
        try {
            while (this.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            element = this.buffer[ptrGet];
            this.buffer[ptrGet] = 0;
            this.ptrGet = (this.ptrGet + 1) % this.maxSize;
            this.size -= 1;
            System.out.printf("Consumed element:" + element + "\n");
            notFull.signal();
        } finally {
            lock.unlock();
        }
            return element;
    }
}
