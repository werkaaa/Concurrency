package lab6;

public class Producer extends Thread {

    private final Monitor monitor;
    private final Buffer buffer;
    private int spot;

    public Producer(Monitor monitor, Buffer buffer) {
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.spot = monitor.startProducing();

                monitor.printThreadsInside();
                sleep(200);
                // do something
                buffer.produce(this.spot);
                monitor.endProducing(this.spot);
            } catch (InterruptedException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}