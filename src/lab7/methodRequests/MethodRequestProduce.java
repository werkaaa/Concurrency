package lab7.methodRequests;

import lab7.Buffer;
import lab7.Future;

import java.util.ArrayList;


public class MethodRequestProduce implements MethodRequest {
    private volatile boolean done = false;
    private final Buffer buffer;
    private final Future future;
    private final int howMany;
    private final int element;
    private final int producerId;


    public MethodRequestProduce(Buffer buffer, Future future, int howMany, int element, int producerId) {
        this.buffer = buffer;
        this.future = future;
        this.howMany = howMany;
        this.element = element;
        this.producerId = producerId;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void execute() {
        ArrayList<Integer> produced = this.buffer.produce(this.howMany, this.element);
        future.set(produced);
        System.out.println("P_" + this.producerId + " produced: " + produced.size() + " elements");
        this.done = true;
    }

    @Override
    public boolean canExecute() {
        return this.buffer.isEnoughPlace(this.howMany);
    }

    public String toString() {
        return "P_" + this.producerId + "(" + this.howMany + ", " + this.done + ")";
    }
}
