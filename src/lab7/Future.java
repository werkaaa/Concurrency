package lab7;

import java.util.ArrayList;


public class Future {
    private volatile boolean ready = false;
    private ArrayList<Integer> value;

    public boolean isReady() {
        return this.ready;
    }

    public void set(ArrayList value) {
        this.value = value;
        this.ready = true;
    }

    public ArrayList<Integer> getValue() {
        return this.value;
    }
}
