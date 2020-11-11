package lab4.random;


public class Producer implements Runnable{
    private Buffer buffer;
    private int bufferSize;

    public Producer(Buffer buffer, int bufferSize){
        this.buffer = buffer;
        this.bufferSize = bufferSize;
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
            int howMany = this.getRandom(this.bufferSize);
            buffer.produce(element, howMany);
        }
    }
}
