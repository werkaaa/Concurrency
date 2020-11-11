package lab3;


public class Producer implements Runnable{
    private Buffer buffer;

    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    private int getRandom(){
        int min = 1;
        int max = 100;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

    @Override
    public void run() {
        while (true) {
            int element = this.getRandom();
            buffer.produce(element);
        }
    }
}
