package lab5.hasWaiters;


public class Consumer implements Runnable{
    private Buffer buffer;
    private int maxConsumed;
    private int id;

    public Consumer(int id, Buffer buffer, int maxConsumed){
        this.id = id;
        this.buffer = buffer;
        this.maxConsumed = maxConsumed;
    }

    private int getRandom(int max){
        int min = 1;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

    @Override
    public void run() {
        while(true) {
            int howMany = this.getRandom(this.maxConsumed);
            buffer.consume(howMany, this.id);
        }
    }
}
