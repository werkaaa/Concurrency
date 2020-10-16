package lab1;

public class HelloThread extends Thread{
    public void run() {
        System.out.println("Hello from a HelloThread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }
}
