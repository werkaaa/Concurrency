package lab2;

import java.util.ArrayList;
import java.util.Arrays;

public class PhilosopherValet implements Runnable{
    private int id;
    private static ArrayList<BinarySemaphore> forks = new ArrayList<>(Arrays.asList(
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore(),
            new BinarySemaphore()
    ));
    private static Semaphore valet = new Semaphore(4);

    public PhilosopherValet(int id){
        this.id = id;
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


        while(true){
            this.think();

            // Valet checks if he can let the philosopher in
            try {
                this.valet.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

            this.valet.release();

        }
    }
}

