package lab5.hasWaiters;


public class Producer implements Runnable{
    private Buffer buffer;
    private int maxProduced;
    private int id;

    public Producer(int id, Buffer buffer, int maxProduced){
        this.id = id;
        this.buffer = buffer;
        this.maxProduced = maxProduced;
    }

    private int getRandom(int max){
        int min = 1;

        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

    @Override
    public void run() {
        while (true) {
            int element = this.getRandom(100);
            int howMany = this.getRandom(this.maxProduced);
            buffer.produce(element, howMany, id);
        }
    }
}
