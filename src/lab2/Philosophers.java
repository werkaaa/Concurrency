package lab2;

import java.util.ArrayList;

public class Philosophers {
    // One philosopher is left-handed
    public static void main(String[] args) {
        ArrayList<Thread> philosophers = new ArrayList<>();
        boolean leftHanded = true;
        for(int i = 0; i < 4; i++){
            philosophers.add(new Thread(new Philosopher(i, false)));
            philosophers.get(i).start();
        }
        if(leftHanded){
            philosophers.add(new Thread(new Philosopher(4, true)));
        }
        else{
            philosophers.add(new Thread(new Philosopher(4, false)));
        }
        philosophers.get(4).start();
    }
}

// Rozwiązanie z lokajem
// Jeden z filozofów zachowuje się inaczej niż pozostali