package lab2;

import java.util.ArrayList;
import java.util.Arrays;

public class Philosopher implements Runnable{
    private int id;
    private static ArrayList<BinarySemaphore> forks = new ArrayList<>(Arrays.asList(
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore()
    ));
    private boolean leftHanded;

    public Philosopher(int id, boolean leftHanded){
        this.id = id;
        this.leftHanded = leftHanded;
    }

    public void think(){
        System.out.println("Philosopher " + this.id + " is thinking");
    }

    public void eat(){
        System.out.println("Philosopher " + this.id + " is eating");
    }

    @Override
    public void run() {

        int first = this.id;
        int second = (this.id+1)%5;

        if (this.leftHanded){
            second = this.id;
            first = (this.id+1)%5;
        }

        while(true){
            this.think();

            // Grabs left fork
            try {
                this.forks.get(first).acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Grabs right fork
            try {
                this.forks.get(second).acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.eat();
            // Puts down left fork
            this.forks.get(first).release();
            // Puts down right fork
            this.forks.get(second).release();

        }
    }
}
