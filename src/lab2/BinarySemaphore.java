package lab2;

public class BinarySemaphore {
    private boolean value = true;

    public synchronized void acquire() throws InterruptedException {
        while(!this.value){
            this.wait();
        }
        this.value = false;
    }

    public synchronized void release(){
        this.value = true;
        this.notify();
    }

}
