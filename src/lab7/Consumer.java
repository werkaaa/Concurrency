package lab7;

import java.util.ArrayList;


public class Consumer implements Runnable {
    private final int id;
    private final ProxyBuffer proxyBuffer;
    private final int maxConsumed;

    public Consumer(int id, ProxyBuffer proxyBuffer, int maxConsumed) {
        this.id = id;
        this.proxyBuffer = proxyBuffer;
        this.maxConsumed = maxConsumed;
    }

    private int getRandom(int max) {
        int min = 1;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

    @Override
    public void run() {
        while (true) {
            int howMany = this.getRandom(this.maxConsumed);
            Future future = proxyBuffer.consume(howMany, this.id);
            while (!future.isReady()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ArrayList<Integer> consumed = future.getValue();
//            System.out.println("C_" + this.id + " consumed: " + consumed.size() + " elements");
        }
    }
}
