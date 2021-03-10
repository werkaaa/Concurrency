package lab7;

import java.util.ArrayList;


public class Producer implements Runnable {

    private final int id;
    private final ProxyBuffer proxyBuffer;
    private final int maxProduced;


    public Producer(int id, ProxyBuffer proxyBuffer, int maxProduced) {
        this.id = id;
        this.proxyBuffer = proxyBuffer;
        this.maxProduced = maxProduced;
    }

    private int getRandom(int max) {
        int min = 1;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        return randomNumber;
    }

    @Override
    public void run() {
        while (true) {
            int element = this.getRandom(100);
            int howMany = this.getRandom(this.maxProduced);
            Future future = proxyBuffer.produce(howMany, element, this.id);
            while (!future.isReady()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) { //W monitorze można dodac na koniec (zadać paramter dodatkowej pracy)
                    e.printStackTrace();
                }
            }
            ArrayList<Integer> produced = future.getValue();
//            System.out.println("P_" + this.id + " produced: " + produced.size() + " elements");
        }
    }
}
