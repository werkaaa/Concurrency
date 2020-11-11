package lab3;


public class Consumer implements Runnable{
    private Buffer buffer;

    public Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true) {
            buffer.consume();
        }
    }
}
