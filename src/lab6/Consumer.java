package lab6;


public class Consumer extends Thread {

    private final Monitor monitor;
    private final Buffer buffer;
    private int spot;

    public Consumer(Monitor monitor, Buffer buffer) {
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.spot = monitor.startConsuming();

                monitor.printThreadsInside();
                sleep(100);
                // do something
                buffer.consume(this.spot);
                monitor.endConsuming(this.spot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}