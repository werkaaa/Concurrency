package lab1;

import java.util.ArrayList;

public class HelloRunnable implements Runnable{

    private static IntegerCounter value = new IntegerCounter(0);
    private int index;

    public HelloRunnable(int index){
        this.index = index;
    }
    public void run() {
        if(this.index % 2 ==0){
            this.value.decrement();
        }
        else{
            this.value.increment();
        }
    }

    public static void main(String[] args) {
        int threadsNumber = 10;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i<threadsNumber; i++){
            threads.add(new Thread(new HelloRunnable(i)));
            threads.get(i).start();
        }

        for(int i = 0; i<threadsNumber; i++){
            try {
                threads.get(i).join();
            } catch(InterruptedException e){
                System.out.println("err");
            }

        }

        System.out.printf(value.toString());
    }
}
