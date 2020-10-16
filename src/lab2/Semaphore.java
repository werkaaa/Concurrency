package lab2;

public class Semaphore {
    private int value;

    public Semaphore(int value){
        this.value = value;
    }

    public synchronized void acquire() throws InterruptedException {
        while(this.value <= 0){
            this.wait();
        }
        this.value--;
    }

    public synchronized void release(){
        this.value++;
        this.notify();
    }
}
