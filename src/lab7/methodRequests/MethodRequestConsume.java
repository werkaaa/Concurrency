package lab7.methodRequests;

import lab7.Buffer;
import lab7.Future;

import java.util.ArrayList;


public class MethodRequestConsume implements MethodRequest {
    private volatile boolean done = false;
    private final Buffer buffer;
    private final Future future;
    private final int howMany;
    private final int consumerId;

    public MethodRequestConsume(Buffer buffer, Future future, int howMany, int consumerId) {
        this.buffer = buffer;
        this.future = future;
        this.howMany = howMany;
        this.consumerId = consumerId;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void execute() {
        ArrayList<Integer> consumed = this.buffer.consume(this.howMany);
        future.set(consumed);
        System.out.println("C_" + this.consumerId + " consumed: " + consumed.size() + " elements");
        this.done = true;
    }

    @Override
    public boolean canExecute() {
        return this.buffer.areEnoughElements(this.howMany);
    }

    public String toString() {
        return "C_" + this.consumerId + "(" + this.howMany + ", " + this.done + ")";
    }
}
