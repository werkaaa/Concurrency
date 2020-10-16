package lab2;

import java.util.ArrayList;

public class PhilosophersValet {
    public static void main(String[] args) {
        ArrayList<Thread> philosophers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            philosophers.add(new Thread(new PhilosopherValet(i)));
            philosophers.get(i).start();
        }
    }
}
