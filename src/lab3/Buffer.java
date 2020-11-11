package lab3;

public class Buffer {

    final int maxSize;
    private int ptrInsert;
    private int ptrGet;
    private int size;
    private Integer[] buffer;

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

    public synchronized void produce(int element){
        while (this.isFull()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer[ptrInsert] = element;
        this.ptrInsert = (this.ptrInsert + 1) % this.maxSize;
        this.size += 1;
        System.out.printf("Produced element:" + element + "\n");
        // Notify bratuje sytuację, ale jest brzydkie bo marnuje procesor
        this.notifyAll();
    }

    public synchronized int consume(){
        while (this.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int element = this.buffer[ptrGet];
        this.buffer[ptrGet] = 0;
        this.ptrGet = (this.ptrGet + 1) % this.maxSize;
        this.size -= 1;
        System.out.printf("Consumed element:" + element + "\n");
        this.notifyAll();
        return element;
    }
}
